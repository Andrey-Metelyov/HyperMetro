package Graph;

public class Distance implements Comparable<Distance> {
    private int to;
    private int distance;

    public Distance(int to, int distance) {
        this.to = to;
        this.distance = distance;
    }

    int to() {
        return to;
    }

    void relax(int distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Distance other) {
        return Integer.compare(this.distance, other.distance);
    }

    @Override
    public String toString() {
        return "-> " + to + '=' + distance;
    }
}