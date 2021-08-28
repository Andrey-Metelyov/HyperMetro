package Metro;

import java.util.List;

public class Station {
    String name;
    List<TransferStation> transfer;
    Integer time;

    public void setTime(int time) {
        this.time = time;
    }

    public Station(String name) {
        this.name = name;
        this.transfer = List.of();
    }

    public Station(String name, List<TransferStation> transfer) {
        this.name = name;
        this.transfer = transfer == null ? List.of() : transfer;
    }

    public Station(String name, List<TransferStation> transfer, int time) {
        this.name = name;
        this.transfer = transfer == null ? List.of() : transfer;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public List<TransferStation> getTransfer() {
        return transfer;
    }

    public int getTime() { return time; }

    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                ", transfer=" + transfer +
                ", time=" + time +
                "}\n";
    }
}
