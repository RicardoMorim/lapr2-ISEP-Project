package pt.ipp.isep.dei.mdisc.sprint3;

/**
 * The type Dijkstra result.
 */
public class DijkstraResult {
    private int[] parents;
    private int[] shortestDistances;

    /**
     * Instantiates a new Dijkstra result.
     *
     * @param parents           the parents
     * @param shortestDistances the shortest distances
     */
    public DijkstraResult(int[] parents, int[] shortestDistances) {
        this.parents = parents;
        this.shortestDistances = shortestDistances;
    }

    /**
     * Get parents int [ ].
     *
     * @return the int [ ]
     */
    public int[] getParents() {
        return parents;
    }

    /**
     * Get shortest distances int [ ].
     *
     * @return the int [ ]
     */
    public int[] getShortestDistances() {
        return shortestDistances;
    }
}
