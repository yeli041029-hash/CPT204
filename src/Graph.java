import java.util.*;

/*
 * PathResult stores the result of one shortest-path query.
 * It includes the full path and the total cost of that path.
 */
class PathResult{
    private List<String> path;
    private double totalDistance;

    /*
     * Creates a path result object.
     */
    public PathResult(List<String> path, double totalDistance) {
        this.path = path;
        this.totalDistance = totalDistance;
    }

    /*
     * Returns the total path cost.
     */
    public double getTotalDistance() {
        return totalDistance;
    }

    /*
     * Returns the node sequence of the path.
     */
    public List<String> getPath() {
        return path;
    }
}

/*
 * Graph stores the whole city network using an adjacency list.
 * Its main job is to support shortest-path queries,
 * including cases with required waypoints.
 */
public class Graph {
    private Map<String, List<Edge>> adjacencyList;

    /*
     * Creates an empty graph.
     */
    public Graph(){
        this.adjacencyList = new HashMap<>();
    }

    /*
     * The graph in this coursework is undirected,
     * so addEdge adds both directions.
     */
    public void addEdge(String u, String v, double weight){
        this.adjacencyList.putIfAbsent(u,new ArrayList<>());
        this.adjacencyList.get(u).add(new Edge(v,weight));

        this.adjacencyList.putIfAbsent(v,new ArrayList<>());
        this.adjacencyList.get(v).add(new Edge(u,weight));
    }

    /*
     * Uses Dijkstra's algorithm to find the shortest path
     * from startNode to endNode.
     */
    public PathResult getShortestPath(String startNode, String endNode){
        // If start and end are the same, the path is just that node with cost 0
        if (startNode.equals(endNode)) {
            List<String> path = new ArrayList<>();
            path.add(startNode);
            return new PathResult(path, 0.0);
        }

        // distances stores the current best known distance to each node
        Map<String, Double> distances = new HashMap<>();

        // previousNodes is used later to rebuild the final path
        Map<String, String> previousNodes = new HashMap<>();

        // The priority queue always processes the node with the smallest distance first
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(Comparator.comparingDouble(NodeDistance::getDistance));

        for (String node : adjacencyList.keySet()){
            distances.put(node, Double.POSITIVE_INFINITY);
        }

        distances.put(startNode, 0.0);
        pq.add(new NodeDistance(startNode,0.0));

        while(!pq.isEmpty()){
            NodeDistance current = pq.poll();
            String u = current.getNodeId();

            // Once the destination is reached, the search can stop early
            if(u.equals((endNode))){
                break;
            }

            // Skip this entry if a better distance is already known
            if (current.getDistance() > distances.get(u)){
                continue;
            }

            for (Edge edge : adjacencyList.getOrDefault(u,new ArrayList<>())){
                String v = edge.getTargetNodeId();
                double weight = edge.getWeight();
                double newDist = distances.get(u) + weight;

                // Update both the distance and the previous node if a shorter path is found
                if (newDist < distances.get(v)) {
                    distances.put(v, newDist);
                    previousNodes.put(v, u);
                    pq.add(new NodeDistance(v, newDist));
                }
            }
        }

        return reconstructPath(previousNodes,distances,startNode,endNode);
    }

    /*
     * Rebuilds the shortest path step by step from the previousNodes map.
     */
    private PathResult reconstructPath(Map<String, String> prev, Map<String, Double> dists, String start, String end) {
        List<String> path = new LinkedList<>();

        // If the destination is still infinity, the two nodes are not connected
        if (!dists.containsKey(end) || dists.get(end) == Double.POSITIVE_INFINITY) {
            return new PathResult(path, Double.POSITIVE_INFINITY);
        }

        // Move backward from the destination until the path is fully rebuilt
        for (String at = end; at != null; at = prev.get(at)) {
            path.add(at);
        }

        // The path is rebuilt backward, so reverse it at the end
        Collections.reverse(path);

        return new PathResult(path, dists.get(end));
    }

    /*
     * NodeDistance is a small helper class for the priority queue.
     * It stores a node ID together with its current distance.
     */
    private static class NodeDistance {
        private String nodeId;
        private double distance;

        /*
         * Creates a node-distance pair.
         */
        public NodeDistance(String nodeId, double distance){
            this.distance = distance;
            this.nodeId = nodeId;
        }

        /*
         * Returns the node ID.
         */
        public String getNodeId(){
            return nodeId;
        }

        /*
         * Returns the current distance.
         */
        public double getDistance(){
            return distance;
        }
    }

    /*
     * Handles cases with required waypoints.
     * The full query is split into smaller shortest-path sections and then merged.
     */
    public PathResult getPathThrough(String[] nodes) {
        List<String> fullPath = new ArrayList<>();
        double totalDistance = 0.0;

        for (int i = 0; i < nodes.length - 1; i++) {
            PathResult part = getShortestPath(nodes[i], nodes[i + 1]);

            // If any section is unreachable, the whole required path fails
            if (part.getTotalDistance() == Double.POSITIVE_INFINITY) {
                return new PathResult(new ArrayList<>(), Double.POSITIVE_INFINITY);
            }

            if (i == 0) {
                fullPath.addAll(part.getPath());
            } else {
                // From the second section onward, remove the repeated joining node
                fullPath.addAll(part.getPath().subList(1, part.getPath().size()));
            }

            totalDistance += part.getTotalDistance();
        }

        return new PathResult(fullPath, totalDistance);
    }
}
