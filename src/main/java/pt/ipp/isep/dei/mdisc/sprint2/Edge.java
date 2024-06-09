package pt.ipp.isep.dei.mdisc.sprint2;


/**
 * The type Edge.
 */
public class Edge {
    /**
     * The Src.
     */
    int src, /**
     * The Dest.
     */
    dest, /**
     * The Weight.
     */
    weight;

    /**
     * Instantiates a new Edge.
     *
     * @param src    the src
     * @param dest   the dest
     * @param weight the weight
     */
    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}

