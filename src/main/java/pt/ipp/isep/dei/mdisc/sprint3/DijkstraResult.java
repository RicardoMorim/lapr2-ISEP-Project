package pt.ipp.isep.dei.mdisc.sprint3;

public class DijkstraResult {
    private int[] parents;
    private int[] shortestDistances;

    public DijkstraResult(int[] parents, int[] shortestDistances) {
        this.parents = parents;
        this.shortestDistances = shortestDistances;
    }

    public int[] getParents() {
        return parents;
    }

    public int[] getShortestDistances() {
        return shortestDistances;
    }
}
