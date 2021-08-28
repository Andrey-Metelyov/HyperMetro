package Metro;

import Graph.Graph;
import Graph.BFS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Route {
    private final Metro metro;
    private final Map<Integer, Station> mapVertexStation = new HashMap<>();
    private final Map<Station, Integer> mapStationVertex = new HashMap<>();
    private int lastDistance;
    private final boolean fastest;
    boolean debug = false;

    public Route(Metro metro, boolean fastest) {
        this.metro = metro;
        this.fastest = fastest;
    }

    public List<String> printable(List<Station> route) {
        List<String> result = new ArrayList<>();
        Station prev = null;
        for (Station station : route) {
            if (prev != null && prev.transfer != null) {
                List<Station> transferStations = prev.transfer
                        .stream()
                        .map(it -> metro.getLine(it.line).getStation(it.station))
                        .collect(Collectors.toList());
                if (transferStations.contains(station)) {
                    result.add("Transition to " + metro.getStationLine(station).name);
                }
            }
            result.add(station.name);
            prev = station;
        }
        return result;
    }

    public List<Station> route(String line1, String station1, String line2, String station2) {
        Graph g = initGraph();
        if (debug) System.err.println(g);
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
        Graph g = createGraph();

        for (MetroLine line : metro.metroLines) {
            for (Station station : line.stations) {
                int current = mapStationVertex.get(station);
                for (String prev : station.prev) {
                    Station prevStation = line.getStation(prev);
                    int to = mapStationVertex.get(prevStation);
                    int time = prevStation.time == null ? 0 : prevStation.time;
                    if (!fastest) time = 1;
                    g.addEdge(current, to, time);
                    if (debug) System.err.println("add edges to prev: " +
                            station.name + " (" + current + ") -> " +
                            prevStation.name + " (" + to + ") " + time);
                }
                for (String next : station.next) {
                    Station nextStation = line.getStation(next);
                    int to = mapStationVertex.get(nextStation);
                    int time = station.time == null ? 0 : station.time;
                    if (!fastest) time = 1;
                    g.addEdge(current, to, time);
                    if (debug) System.err.println("add edges to next: " +
                            station.name + " (" + current + ") -> " +
                            nextStation.name + " (" + to + ") " + time);
                }
                for (TransferStation ts : station.transfer) {
                    if (debug) System.err.println(ts);
                    Station transferStation = metro.getLine(ts.line).getStation(ts.station);
                    int to = mapStationVertex.get(transferStation);
                    int time = 0;
                    if (fastest) time = 5;
                    g.addEdge(current, to, time);
                    if (debug) System.err.println("add tr edges: " +
                            station.name + " (" + current + ") -> " +
                            transferStation.name + " (" + to + ") " + time);
                }
            }
        }
        return g;
    }

    private Graph createGraph() {
        int curVertex = 0;
        for (MetroLine line : metro.metroLines) {
            for (Station station : line.stations) {
                mapVertexStation.put(curVertex, station);
                mapStationVertex.put(station, curVertex++);
            }
        }
        return new Graph(mapStationVertex.size());
    }

    public int getLastDistance() {
        return lastDistance;
    }
}