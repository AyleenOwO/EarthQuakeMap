package cl.ucn.disc.dsm.atorres.earthquakemap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MainActivity extends AppCompatActivity {

    /**
     *
     */
    MapView map = null;

    /**
     *
     * @param savedInstanceState
     */
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        setContentView(R.layout.activity_main);

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        MapController mapController = (MapController) map.getController();
        mapController.setZoom(9.5);

        //Epicentro terremoto coquimbo
        GeoPoint startPoint = new GeoPoint(-30.132, -71.405);
        mapController.setCenter(startPoint);

        //marcador de prueba
        Marker initialMarker = new Marker(map);
        initialMarker.setTitle("Epicentro terremoto Chile 2019");
        initialMarker.setPosition(startPoint);
        initialMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(initialMarker);
    }

    /**
     *
     */
    public void onResume(){
        super.onResume();
        map.onResume();
    }

    /**
     *
     */
    public void onPause(){
        super.onPause();
        map.onPause();
    }
}

