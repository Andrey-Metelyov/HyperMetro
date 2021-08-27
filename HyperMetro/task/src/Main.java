import Metro.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String filename;
        if (args.length == 0) {
            System.out.println("No file specified");
            filename = "./HyperMetro/task/test/baltimore.json";
//            return;
        } else {
            filename = args[0];
        }
        System.err.println(Arrays.toString(args));
        System.err.println(Paths.get("").toAbsolutePath());
        try {
            String json = Files.readString(Paths.get(filename));
            System.err.println(json);
            Metro metro = Metro.deserialize(json);
//            Map<String, MetroLinkedList> lists = readJson(json);
//            System.err.println(lists);
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
            switch (command.get(0)) {
                case "/append": {
                    String line = command.get(1);
                    Station station = new Station(command.get(2), null);
                    System.err.println(line);
                    System.err.println(station);
                    metro.getLine(line).add(station);
                    break;
                }
                case "/add-head": {
                    String line = command.get(1);
                    Station station = new Station(command.get(2), null);
                    metro.getLine(line).addFirst(station);
                    break;
                }
                ///connect "Linka C" "I.P.Pavlova" "Linka A" "Petriny"
                case "/connect": {
                    String line1 = command.get(1);
                    String station1 = command.get(2);
                    String line2 = command.get(3);
                    String station2 = command.get(4);
                    metro.connect(line1, station1, line2, station2);
                    break;
                }
//                case "/remove": {
//                    String line = command.get(1);
//                    Station station = new Station(command.get(2), null);
//                    metro.getLine(line).remove(station);
//                    break;
//                }
                case "/output": {
                    String line = command.get(1);
                    Collection<Station> stations = metro.getLine(line).getStations();
                    System.out.println("depot");
                    System.out.println(
                            stations.stream()
                                    .map(it -> {
                                        return it.getName()
                                                + (it.getTransfer().isEmpty() ? "" :
                                                " - " + it.getTransfer().get(0).getStation()
                                                + " ("
                                                        + it.getTransfer().get(0).getLine()
                                                + ')');
                                    })
                                    .collect(Collectors.joining("\n"))
                    );
                    System.out.println("depot");
                    break;
                }
                case "/exit":
                    return;
                default:
            }
        }
    }

//    private static Map<String, MetroLinkedList> readJson(String json) {
//        Map<String, MetroLinkedList> result = new HashMap<>();
//        try (JsonReader reader = new JsonReader(new StringReader(json))) {
//            String name = "";
//            int stationNumber = 0;
//            String stationName = "";
//            List<Station> transfer = null;
//            MetroLinkedList current = null;
//            State state = State.GLOBAL;
//            int counter = 0;
//            while (reader.hasNext()) {
//                while (reader.hasNext()) {
//                    counter++;
//                    JsonToken nextToken = reader.peek();
//                    System.err.print("\t");
//                    System.err.print(counter);
//                    System.err.print(" ");
//                    System.err.print(state);
//                    System.err.print("\t");
//                    System.err.println(nextToken);
//                    if (JsonToken.BEGIN_OBJECT.equals(nextToken)) {
//                        reader.beginObject();
//                        if (state == State.GLOBAL) {
//                            state = State.LINE;
//                        } else if (state == State.LINE) {
//                            current = new MetroLinkedList();
//                            System.err.println("create new line");
//                            System.err.println("add line " + name);
//                            result.put(name, current);
//                            System.err.println("res:" + result);
//                            System.err.println("cur:" + current);
//                            state = State.STATION;
//                        } else if (state == State.STATION) {
//                            state = State.STATION_DETAILS;
//                        }
//                    } else if (JsonToken.NAME.equals(nextToken)) {
//                        name = reader.nextName();
//                        if (state == State.STATION) {
//                            stationNumber = Integer.parseInt(name);
//                            System.err.println("stationNumber=" + stationNumber);
//                        }
//                        System.err.println("name='" + name + "'");
//                    } else if (JsonToken.STRING.equals(nextToken)) {
//                        stationName = reader.nextString();
//                        state = State.TRANSFER;
//                    } else if (JsonToken.BEGIN_ARRAY.equals(nextToken)) {
//                        reader.beginArray();
//                        transfer = new ArrayList<>();
////                        state = State.TRANSFER;
//                    }
//                }
//                System.err.println("end of object");
//                if (state == State.TRANSFER) {
//                    current.add(new Station(stationNumber, stationName, transfer));
//                    System.err.println("add station " + stationName);
//                    System.err.println("res:" + result);
//                    System.err.println("cur:" + current);
//                    reader.endArray();
//                    state = State.STATION;
//                } else {
//                    state = State.LINE;
//                }
//                reader.endObject();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
}
