package Metro;

public class TransferStation {
    String line;
    String station;

    public TransferStation(String line, String station) {
        this.line = line;
        this.station = station;
    }

    public String getLine() {
        return line;
    }

    public String getStation() {
        return station;
    }

    @Override
    public String toString() {
        return "TransferStation{" +
                "line='" + line + '\'' +
                ", station='" + station + '\'' +
                '}';
    }
}
