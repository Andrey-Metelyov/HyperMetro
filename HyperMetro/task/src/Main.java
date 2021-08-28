import Metro.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        boolean debug = false;
        String filename;
        if (args.length == 0) {
            System.out.println("No file specified");
            filename = "./HyperMetro/task/test/london.json";
        } else {
            filename = args[0];
        }
        System.err.println(Arrays.toString(args));
        System.err.println(Paths.get("").toAbsolutePath());
        try {
            String json = Files.readString(Paths.get(filename));
            if (debug) System.err.println(json);
            Metro metro = Metro.deserialize(json);
            if (debug) System.err.println(metro);
            guiLoop(metro);
        } catch (NoSuchFileException e) {
            System.out.println("Error. File " + filename + " doesn't exists");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> parse(String command) {
        List<String> list = new ArrayList<>();
        String[] commands = command.split("\\s+", 2);
        list.add(commands[0]);
        if (commands.length > 1) {
            String params = commands[1];
            while (!params.isEmpty()) {
                if (params.charAt(0) == '"') {
                    int closeQuotePos = params.indexOf('\"', 1);
                    list.add(params.substring(1, closeQuotePos));
                    params = params.substring(closeQuotePos + 1).trim();
                } else {
                    int nextSpacePos = params.indexOf(' ');
                    if (nextSpacePos != -1) {
                        list.add(params.substring(0, nextSpacePos));
                        params = params.substring(nextSpacePos + 1).trim();
                    } else {
                        list.add(params);
                        params = "";
                    }
                }
            }
        }
        return list;
    }

    private static void guiLoop(Metro metro) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            List<String> command = parse(scanner.nextLine());
            System.err.println("command: " + command);
            switch (command.get(0)) {
                case "/fastest-route":
                case "/route": {
                    String line1 = command.get(1);
                    String station1 = command.get(2);
                    String line2 = command.get(3);
                    String station2 = command.get(4);
                    Route r = new Route(metro, command.get(0).equals("/fastest-route"));
                    List<String> route = r.printable(r.route(line1, station1, line2, station2));
                    for (String line : route) {
                        System.out.println(line);
                    }
                    if (command.get(0).equals("/fastest-route")) {
                        System.out.println("Total time: " + r.getLastDistance());
                    }
                }
                break;
//                case "/append": {
//                    String line = command.get(1);
//                    Station station = new Station(command.get(2), null);
//                    System.err.println(line);
//                    System.err.println(station);
//                    metro.getLine(line).add(station);
//                    break;
//                }
//                case "/add-head": {
//                    String line = command.get(1);
//                    Station station = new Station(command.get(2), null);
//                    metro.getLine(line).addFirst(station);
//                    break;
//                }
//                case "/connect": {
//                    String line1 = command.get(1);
//                    String station1 = command.get(2);
//                    String line2 = command.get(3);
//                    String station2 = command.get(4);
//                    metro.connect(line1, station1, line2, station2);
//                    break;
//                }
//                case "/output": {
//                    String line = command.get(1);
//                    Collection<Station> stations = metro.getLine(line).getStations();
//                    System.out.println("depot");
//                    System.out.println(
//                            stations.stream()
//                                    .map(it -> {
//                                        return it.getName()
//                                                + (it.getTransfer().isEmpty() ? "" :
//                                                " - " + it.getTransfer().get(0).getStation()
//                                                + " ("
//                                                        + it.getTransfer().get(0).getLine()
//                                                + ')');
//                                    })
//                                    .collect(Collectors.joining("\n"))
//                    );
//                    System.out.println("depot");
//                    break;
//                }
                case "/exit":
                    return;
                default:
            }
        }
    }

}
