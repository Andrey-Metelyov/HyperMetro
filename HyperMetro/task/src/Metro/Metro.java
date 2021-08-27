package Metro;

import Graph.Graph;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.*;

public class Metro {
    List<MetroLine> metroLines = new ArrayList<>();

    public void connect(String line1Name, String station1Name, String line2Name, String station2Name) {
        MetroLine metroLine1 = getLine(line1Name);
        MetroLine metroLine2 = getLine(line2Name);
        Station station1 = metroLine1.getStations().stream()
                .filter(it -> it.getName().equals(station1Name))
                .findFirst().orElse(null);
        station1.getTransfer().add(new TransferStation(line2Name, station2Name));
        Station station2 = metroLine2.getStations().stream()
                .filter(it -> it.getName().equals(station2Name))
                .findFirst().orElse(null);
        station2.getTransfer().add(new TransferStation(line1Name, station1Name));
    }

    public List<String> route(String line1, String station1, String line2, String station2) {
        Graph g = initGraph();
        System.err.println(g);
        return List.of();
    }

    private Graph initGraph() {
        Map<Integer, Station> mapVertexStation = new HashMap<>();
        Map<Station, Integer> mapStationVertex = new HashMap<>();
        int curVertex = 0;
        for (MetroLine line : metroLines) {
            for (Station station : line.getStations()) {
                mapVertexStation.put(curVertex, station);
                mapStationVertex.put(station, curVertex++);
            }
        }
        Graph g = new Graph(mapStationVertex.size());
        for (MetroLine line : metroLines) {
            List<Station> stations = line.getStations();
            Station prev = null;
            for (Station station : stations) {
                if (prev != null) {
                    int v = mapStationVertex.get(prev);
                    int w = mapStationVertex.get(station);
                    g.addEdge(v, w);
                    g.addEdge(w, v);
                    System.err.println("add edges: " + v + " <-> " + w);
                }
                prev = station;
                List<TransferStation> transfer = station.getTransfer();
                for (TransferStation ts : transfer) {
                    System.err.println(ts);
                    int v = mapStationVertex.get(station);
                    MetroLine transferLine = getLine(ts.line);
                    Station transferStation = transferLine.getStation(ts.getStation());
                    int w = mapStationVertex.get(transferStation);
                    g.addEdge(v, w);
                    System.err.println("add tr edges: " + v + " -> " + w);
                }
            }
        }
        return g;
    }

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
//        System.err.println(metro);
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
