/*
 * Edge represents one edge in the graph.
 * It stores the target node and the weight of that edge.
 */
public class Edge {
    private String targetNodeId;
    private double weight;

    /*
     * Creates one edge object.
     */
    public Edge(String targetNodeId, double weight){
        this.targetNodeId = targetNodeId;
        this.weight = weight;
    }

    /*
     * Returns the target node connected by this edge.
     */
    public String getTargetNodeId(){
        return targetNodeId;
    }

    /*
     * Returns the weight of the edge, which is the path cost.
     */
    public double getWeight(){
        return weight;
    }
}
