package Metro;

import Graph.Graph;
import Graph.BFS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Route {
    private final Metro metro;
    private final Map<Integer, Station> mapVertexStation = new HashMap<>();
    private final Map<Station, Integer> mapStationVertex = new HashMap<>();
    private int lastDistance;

    public Route(Metro metro) {
        this.metro = metro;
    }

    public List<Station> route(String line1, String station1, String line2, String station2) {
        Graph g = initGraph();
        System.err.println(g);
        BFS bfs = new BFS(g);
        Station st1 = metro.getLine(line1).getStation(station1);
        Station st2 = metro.getLine(line2).getStation(station2);
        System.err.println("route: " + st1 + " -> " + st2);
        List<Integer> path = bfs.search(
                mapStationVertex.get(st1),
                mapStationVertex.get(st2));
        lastDistance = bfs.lastDistance(mapStationVertex.get(st2));
        return path.stream().map(mapVertexStation::get)
                .collect(Collectors.toList());
    }

    private Graph initGraph() {
        int curVertex = 0;
        for (MetroLine line : metro.metroLines) {
            for (Station station : line.getStations()) {
                mapVertexStation.put(curVertex, station);
                mapStationVertex.put(station, curVertex++);
            }
        }
        Graph g = new Graph(mapStationVertex.size());
        for (MetroLine line : metro.metroLines) {
            List<Station> stations = line.getStations();
            Station prev = null;
            for (Station station : stations) {
                if (prev != null) {
                    int v = mapStationVertex.get(prev);
                    int w = mapStationVertex.get(station);
                    g.addEdge(v, w, prev.time == null ? 1 : prev.time);
                    g.addEdge(w, v, prev.time == null ? 1 : prev.time);
                    System.err.println("add edges: " + v + " <-> " + w + " " + (prev.time == null ? 1 : prev.time));
                }
                prev = station;
                List<TransferStation> transfer = station.getTransfer();
                for (TransferStation ts : transfer) {
                    System.err.println(ts);
                    int v = mapStationVertex.get(station);
                    MetroLine transferLine = metro.getLine(ts.line);
                    Station transferStation = transferLine.getStation(ts.getStation());
                    int w = mapStationVertex.get(transferStation);
                    g.addEdge(v, w, transferStation.time == null ? 0 : 5);
                    System.err.println("add tr edges: " + v + " -> " + w + " " + (transferStation.time == null ? 0 : 5));
                }
            }
        }
        return g;
    }

    public int getLastDistance() {
        return lastDistance;
    }
}