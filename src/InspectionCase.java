import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InspectionCase {
    private String caseName;
    private String[] nodes;

    public InspectionCase(String caseName, String[] nodes) {
        this.caseName = caseName;
        this.nodes = Arrays.copyOf(nodes, nodes.length);
    }

    public String getCaseName() {
        return caseName;
    }

    public String[] getNodes() {
        return Arrays.copyOf(nodes, nodes.length);
    }

    public String getStartingNode() {
        return nodes[0];
    }

    public String getDestinationNode() {
        return nodes[nodes.length - 1];
    }

    public List<String> getRequiredWaypoints() {
        ArrayList<String> requiredWaypoints = new ArrayList<>();

        for (int i = 1; i < nodes.length - 1; i++) {
            requiredWaypoints.add(nodes[i]);
        }

        return requiredWaypoints;
    }
}
