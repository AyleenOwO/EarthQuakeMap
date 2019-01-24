package cl.ucn.disc.dsm.atorres.earthquakemap.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.IOException;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import cl.ucn.disc.dsm.atorres.earthquakemap.Controller;
import cl.ucn.disc.dsm.atorres.earthquakemap.model.QuakeInfo;
import cl.ucn.disc.dsm.atorres.earthquakemap.R;

public class MainActivity extends AppCompatActivity {

    /**
     *
     */
    MapView map = null;

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        setContentView(R.layout.activity_main);

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

    }

    /**
     *
     */
    @Override
    protected void onStart() {
        super.onStart();
        loadData();

    }

    /**
     *
     */
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    /**
     *
     */
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    /**
     * Carga los datos desde la API
     */
    private void loadData() {
        AsyncTask.execute(() -> {

            try {

                final List<QuakeInfo> info = Controller.getCatalogs();

                createMarkers(info);

                runOnUiThread(() -> {
                    lastEarthQuake(info.get(0));
                });

            } catch (IOException e) {
                Log.e("Error", "Error al cargar datos", e);
            }


        });

    }

    /**
     * Crea los marcadores de cada punto
     */
    private void createMarkers(List<QuakeInfo> infoList) {
        for (QuakeInfo data : infoList) {
            GeoPoint startPoint = new GeoPoint(data.geometry.getLatitude(), data.geometry.getLongitude());

            Marker marker = new Marker(map);
            marker.setTitle(data.properties.title);
            marker.setSnippet(data.geometry.toString());
            marker.setPosition(startPoint);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            map.getOverlays().add(marker);
        }
    }


    /**
     * Dirige hacia el punto donde fue el ultimo terremoto
     *
     * @param last
     */
    private void lastEarthQuake(QuakeInfo last) {

        Toast.makeText(this, "ultimo terremoto producido en " + last.properties.title, Toast.LENGTH_SHORT).show();

        map.getController().animateTo(
                new GeoPoint(last.geometry.getLatitude(), last.geometry.getLongitude()),
                8.0,
                3000L
        );
    }
}

