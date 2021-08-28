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
        Graph g = createGraph();

        for (MetroLine line : metro.metroLines) {
            for (Station station : line.stations) {
                int current = mapStationVertex.get(station);
                for (String prev : station.prev) {
                    Station prevStation = line.getStation(prev);
                    int from = mapStationVertex.get(prevStation);
                    int time = prevStation.time == null ? 1 : prevStation.time;
                    g.addEdge(from, current, time);
                    System.err.println("add edges: " + from + " <-> " + current + " " + time);
                }
                for (String next : station.next) {
                    Station nextStation = line.getStation(next);
                    int to = mapStationVertex.get(nextStation);
                    int time = nextStation.time == null ? 1 : nextStation.time;
                    g.addEdge(current, to, time);
                    System.err.println("add edges: " + current + " <-> " + to + " " + time);
                }
                for (TransferStation ts : station.transfer) {
                    System.err.println(ts);
                    Station transferStation = metro.getLine(ts.line).getStation(ts.station);
                    int to = mapStationVertex.get(transferStation);
                    int time = 0;
                    g.addEdge(current, to, time);
                    System.err.println("add tr edges: " + current + " -> " + to + " " + time);
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