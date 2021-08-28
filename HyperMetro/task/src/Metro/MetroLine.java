package Metro;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class MetroLine {
    String name;
    private List<Station> stations = new ArrayList<>();

//    public List<Station> getStations() {
//        return stations.entrySet().stream()
//                .sorted(Map.Entry.comparingByKey())
//                .map(Map.Entry::getValue)
//                .collect(Collectors.toList());
//    }
//
//    public Station getStation(String name) {
//        return stations.values()
//                .stream()
//                .filter(station -> station.name.equals(name))
//                .findFirst().get();
//
//    }
//
//    public void addFirst(Station station) {
//        stations.put(0, station);
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String key) {
//        this.name = name;
//    }
//
//    static class MetroLineSerializer implements JsonSerializer<MetroLine> {
//        @Override
//        public JsonElement serialize(MetroLine src, Type typeOfSrc, JsonSerializationContext context) {
//            JsonObject result = new JsonObject();
//            for(Map.Entry<Integer, Station> entry : src.stations.entrySet()) {
//                result.add(entry.getKey().toString(), context.serialize(entry.getValue()));
//            }
//            return result;
//        }
//    }
//
    static class MetroLineDeserializer implements JsonDeserializer<MetroLine> {
        @Override
        public MetroLine deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            MetroLine metroLine = new MetroLine();
            JsonArray jsonArray = json.getAsJsonArray();
            for (JsonElement element : jsonArray) {
                Station station = context.deserialize(element, Station.class);
                metroLine.stations.add(station);
            }
            return metroLine;
        }
    }
//
//    public void add(Station station) {
//        stations.put(stations.size() + 1, station);
//        System.err.println(stations);
//    }
//
//    public void add(String name) {
//        Station station = new Station(name);
//        stations.put(stations.size() + 1, station);
//        System.err.println(stations);
//    }

    @Override
    public String toString() {
        return "MetroLine{" +
                "name='" + name + '\'' +
                ", stations=" + stations +
                "}\n";
    }
}
