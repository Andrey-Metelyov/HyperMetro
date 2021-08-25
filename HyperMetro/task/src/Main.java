import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No file specified");
            return;
        }
        String filename = args[0];
        System.err.println(Arrays.toString(args));
        MetroLinkedList<String> list;
        try(Stream<String> stream = Files.lines(Paths.get(filename)) ) {
            list = new MetroLinkedList<String>(stream.toArray(String[]::new));
            System.err.println(list);
            list.printTriples("depot", "depot");
        } catch (IOException e) {
            System.out.println("Error! Such a file doesn't exist!");
        }
    }
}
