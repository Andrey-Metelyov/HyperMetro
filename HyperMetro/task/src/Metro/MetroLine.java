package Metro;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class MetroLine {
    String name;
    Map<String, Station> stations = new HashMap<>();

    public Collection<Station> getStations() {
//        System.err.println(stations);
//        List<String> sorted = stations.entrySet()
//                .stream()
//                .sorted(Map.Entry.comparingByKey(new AlphanumComparator()))
//                .map(it -> it.getKey() + " - " + it.getValue())
//                .collect(Collectors.toList());
//        System.err.println(sorted);
        return stations.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(new AlphanumComparator()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public void addFirst(Station station) {
        stations.put("0", station);
    }

    static class MetroLineSerializer implements JsonSerializer<MetroLine> {
        @Override
        public JsonElement serialize(MetroLine src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            for(Map.Entry<String, Station> entry : src.stations.entrySet()) {
                result.add(entry.getKey(), context.serialize(entry.getValue()));
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
                metroLine.stations.put(entry.getKey(), station);
            }
            return metroLine;
        }
    }

    public void add(Station station) {
        stations.put(String.valueOf(stations.size() + 1), station);
    }

    public void add(String name) {
        Station station = new Station(name);
        stations.put(String.valueOf(stations.size() + 1), station);
    }

    @Override
    public String toString() {
        return "MetroLine{" +
                "name='" + name + '\'' +
                ", stations=" + stations +
                "}\n";
    }
}
