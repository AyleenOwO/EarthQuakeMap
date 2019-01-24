package cl.ucn.disc.dsm.atorres.earthquakemap.adapters;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Obtiene el elemento asignado (features) desde el JSOn
 * @param <T>
 */
public class Deserializer<T> implements JsonDeserializer<T> {

    /**
     *
     * @param json
     * @param typeOfT
     * @param context
     * @return una nueva instancia de Gson para evitar una deserializacion infinita
     * @throws JsonParseException
     */
    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonElement quakeData = json.getAsJsonObject().get("features");

        return new Gson().fromJson(quakeData, typeOfT);
    }
}