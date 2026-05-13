import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * InspectionCase stores one query case from Task B.
 * Instead of storing start, end, and waypoints separately,
 * this class keeps them together in the nodes array in the required order.
 */
public class InspectionCase {
    private String caseName;
    private String[] nodes;

    /*
     * Creates a new case and copies the node array
     * to avoid changing the original data outside the class.
     */
    public InspectionCase(String caseName, String[] nodes) {
        this.caseName = caseName;
        this.nodes = Arrays.copyOf(nodes, nodes.length);
    }

    /*
     * Returns the case name, such as Case 1 or Case 2.
     */
    public String getCaseName() {
        return caseName;
    }

    /*
     * Returns the full node sequence used in this query.
     */
    public String[] getNodes() {
        return Arrays.copyOf(nodes, nodes.length);
    }

    /*
     * The first node in the array is the starting node.
     */
    public String getStartingNode() {
        return nodes[0];
    }

    /*
     * The last node in the array is the destination node.
     */
    public String getDestinationNode() {
        return nodes[nodes.length - 1];
    }

    /*
     * All middle nodes are treated as required waypoints.
     * For example, in A1 -> B5 -> C1, B5 will be extracted as a waypoint.
     */
    public List<String> getRequiredWaypoints() {
        ArrayList<String> requiredWaypoints = new ArrayList<>();

        for (int i = 1; i < nodes.length - 1; i++) {
            requiredWaypoints.add(nodes[i]);
        }

        return requiredWaypoints;
    }
}
