import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("No file specified");
            return;
        }
        String filename = args[0];
        System.err.println(Arrays.toString(args));
        String json = Files.readString(Paths.get(filename));
        System.err.println(json);
        Gson gson = new Gson();
        List<List<String>> list = gson.fromJson(json, new TypeToken<List<List<String>>>(){}.getType());
        System.err.println(list);
//        MetroLinkedList<String> list;
//        try(Stream<String> stream = Files.lines(Paths.get(filename)) ) {
//            list = new MetroLinkedList<String>(stream.toArray(String[]::new));
//            System.err.println(list);
//            list.printTriples("depot", "depot");
//        } catch (IOException e) {
//            System.out.println("Error! Such a file doesn't exist!");
//        }
    }
}
