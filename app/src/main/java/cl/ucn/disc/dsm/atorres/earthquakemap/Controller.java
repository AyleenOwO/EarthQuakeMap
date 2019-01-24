package cl.ucn.disc.dsm.atorres.earthquakemap;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import cl.ucn.disc.dsm.atorres.earthquakemap.adapters.Deserializer;
import cl.ucn.disc.dsm.atorres.earthquakemap.model.QuakeInfo;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


public class Controller {

    /**
     *
     */
    public interface CatalogService {

        @GET("query?format=geojson&starttime=2019-1-23&limit=100&orderby=time-asc")
        Call<List<QuakeInfo>> getCatalog();

    }

    /**
     * Des - serializador gson
     */
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(List.class, new Deserializer<List<QuakeInfo>>())
            .create();

    /**
     * Interceptor
     */
    private static final HttpLoggingInterceptor interceptor;

    static {
        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
    }

    /**
     * El cliente
     */
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor).build();

    /**
     * Retrofit
     */
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://earthquake.usgs.gov/fdsnws/event/1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    /**
     * El servicio
     */
    private static final CatalogService catalogService = retrofit.create(CatalogService.class);


    /**
     * @return
     * @throws IOException
     */
    public static List<QuakeInfo> getCatalogs() throws IOException {

        final Call<List<QuakeInfo>> catalogCall = catalogService.getCatalog();

        final List<QuakeInfo> catalog = catalogCall.execute().body();

        Log.d("TAG", "-> SIZE " + catalog.size());

        return catalog;

    }

}
