package Metro;

import java.util.List;

public class Station {
    String name;
    List<TransferStation> transfer;

    public Station(String name) {
        this.name = name;
        this.transfer = List.of();
    }

    public Station(String name, List<TransferStation> transfer) {
        this.name = name;
        this.transfer = transfer == null ? List.of() : transfer;
    }

    public String getName() {
        return name;
    }

    public List<TransferStation> getTransfer() {
        return transfer;
    }

    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                ", transfer=" + transfer +
                "}\n";
    }
}
