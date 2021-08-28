package Metro;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Metro {
    List<MetroLine> metroLines = new ArrayList<>();

//    public void connect(String line1Name, String station1Name, String line2Name, String station2Name) {
//        MetroLine metroLine1 = getLine(line1Name);
//        MetroLine metroLine2 = getLine(line2Name);
//        Station station1 = metroLine1.getStations().stream()
//                .filter(it -> it.getName().equals(station1Name))
//                .findFirst().orElse(null);
//        station1.getTransfer().add(new TransferStation(line2Name, station2Name));
//        Station station2 = metroLine2.getStations().stream()
//                .filter(it -> it.getName().equals(station2Name))
//                .findFirst().orElse(null);
//        station2.getTransfer().add(new TransferStation(line1Name, station1Name));
//    }

//    static class MetroSerializer implements JsonSerializer<Metro> {
//        @Override
//        public JsonElement serialize(Metro src, Type typeOfSrc, JsonSerializationContext context) {
//            JsonObject result = new JsonObject();
//            for(MetroLine metroLine : src.metroLines) {
//                result.add(metroLine.getName(), context.serialize(metroLine));
//            }
//            return result;
//        }
//    }

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

//    public String serialize() {
//        Gson gson = new GsonBuilder()
//                .setPrettyPrinting()
//                .registerTypeAdapter(Metro.class, new MetroSerializer())
//                .registerTypeAdapter(MetroLine.class, new MetroLine.MetroLineSerializer())
//                .create();
//        return gson.toJson(this);
//    }

    public static Metro deserialize(String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Metro.class, new MetroDeserializer())
                .registerTypeAdapter(MetroLine.class, new MetroLine.MetroLineDeserializer())
                .create();
//                System.err.println(metro);
        return gson.fromJson(json, Metro.class);
    }

    public MetroLine getLine(String line) {
//        System.err.println("getLine(" + line + ")");
        return metroLines.stream()
                .filter(it -> it.name.equals(line))
                .findFirst()
                .orElse(null);
    }
//
    public MetroLine getStationLine(Station station) {
        for (MetroLine metroLine : metroLines) {
            if (metroLine.stations.contains(station)) {
                return metroLine;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Metro{" +
                "metroLines=" + metroLines +
                '}';
    }

/*
    public static void main(String[] args) {
        try {
            Metro metro = Metro.deserialize(Files.readString(Paths.get("./HyperMetro/task/test/prague.json")));
            System.out.println(metro);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/
}
