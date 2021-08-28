package Metro;

import java.util.List;

public class Station {
    String name;
    List<String> prev;
    List<String> next;
    List<TransferStation> transfer;
    Integer time;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                ", transfer=" + transfer +
                ", time=" + time +
                "}\n";
    }
}
