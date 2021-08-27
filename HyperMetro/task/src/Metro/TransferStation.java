package Metro;

class TransferStation {
    String line;
    String station;

    public TransferStation(String line, String station) {
        this.line = line;
        this.station = station;
    }

    @Override
    public String toString() {
        return "TransferStation{" +
                "line='" + line + '\'' +
                ", station='" + station + '\'' +
                '}';
    }
}
