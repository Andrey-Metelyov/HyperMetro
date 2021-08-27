package Metro;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class MetroLine {
    String name;
    private final Map<Integer, Station> stations = new HashMap<>();

    public List<Station> getStations() {
//        System.err.println(stations);
//        List<String> sorted = stations.entrySet()
//                .stream()
//                .sorted(Map.Entry.comparingByKey(new AlphanumComparator()))
//                .map(it -> it.getKey() + " - " + it.getValue())
//                .collect(Collectors.toList());
//        System.err.println(sorted);
        return stations.entrySet().stream()
//                .sorted(Map.Entry.comparingByKey(new AlphanumComparator()))
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public Station getStation(String name) {
        return stations.values()
                .stream()
                .filter(station -> station.name.equals(name))
                .findFirst().get();

    }

    public void addFirst(Station station) {
        stations.put(0, station);
    }

    public String getName() {
        return name;
    }

    public void setName(String key) {
        this.name = name;
    }

    static class MetroLineSerializer implements JsonSerializer<MetroLine> {
        @Override
        public JsonElement serialize(MetroLine src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            for(Map.Entry<Integer, Station> entry : src.stations.entrySet()) {
                result.add(entry.getKey().toString(), context.serialize(entry.getValue()));
            }
            return result;
        }
    }

    static class MetroLineDeserializer implements JsonDeserializer<MetroLine> {
        @Override
        public MetroLine deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            MetroLine metroLine = new MetroLine();
            JsonObject jsonObject = json.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                Station station = context.deserialize(entry.getValue(), Station.class);
                metroLine.stations.put(Integer.parseInt(entry.getKey()), station);
            }
            return metroLine;
        }
    }

    public void add(Station station) {
        stations.put(stations.size() + 1, station);
        System.err.println(stations);
    }

    public void add(String name) {
        Station station = new Station(name);
        stations.put(stations.size() + 1, station);
        System.err.println(stations);
    }

    @Override
    public String toString() {
        return "MetroLine{" +
                "name='" + name + '\'' +
                ", stations=" + stations +
                "}\n";
    }
}
