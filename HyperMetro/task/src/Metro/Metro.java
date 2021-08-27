package Metro;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Metro {
    List<MetroLine> metroLines = new ArrayList<>();

    static class MetroSerializer implements JsonSerializer<Metro> {
        @Override
        public JsonElement serialize(Metro src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            for(MetroLine metroLine : src.metroLines) {
                result.add(metroLine.name, context.serialize(metroLine));
            }
            return result;
        }
    }

    static class MetroDeserializer implements JsonDeserializer<Metro> {
        @Override
        public Metro deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Metro metro = new Metro();
            JsonObject jsonObject = json.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                MetroLine metroLine = context.deserialize(entry.getValue(), MetroLine.class);
                metroLine.name = entry.getKey();
                metro.metroLines.add(metroLine);
            }
            return metro;
        }
    }

    public String serialize() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Metro.class, new MetroSerializer())
                .registerTypeAdapter(MetroLine.class, new MetroLine.MetroLineSerializer())
                .create();
        return gson.toJson(this);
    }

    public static Metro deserialize(String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Metro.class, new MetroDeserializer())
                .registerTypeAdapter(MetroLine.class, new MetroLine.MetroLineDeserializer())
                .create();
        Metro metro = gson.fromJson(json, Metro.class);
        System.err.println(metro);
        return metro;
    }

    public MetroLine getLine(String line) {
        System.err.println("getLine(" + line + ")");
        return metroLines.stream()
                .filter(it -> it.name.equals(line))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "Metro{" +
                "metroLines=" + metroLines +
                '}';
    }
}
