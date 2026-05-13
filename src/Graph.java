import java.nio.file.Path;
import java.util.*;

class PathResult{
    private List<String> path;
    private double totalDistance;


    public PathResult(List<String> path, double totalDistance) {
        this.path = path;
        this.totalDistance = totalDistance;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public List<String> getPath() {
        return path;
    }
}


public class Graph {
    private Map<String, List<Edge>> adjacencyList;

    public Graph(){
        this.adjacencyList = new HashMap<>();
    }

    public void addEdge(String u, String v, double weight){
        this.adjacencyList.putIfAbsent(u,new ArrayList<>());
        this.adjacencyList.get(u).add(new Edge(v,weight));

        this.adjacencyList.putIfAbsent(v,new ArrayList<>());
        this.adjacencyList.get(v).add(new Edge(u,weight));
    }

    public PathResult getShortestPath(String startNode, String endNode){
        if (startNode.equals(endNode)) {
            List<String> path = new ArrayList<>();
            path.add(startNode);
            return new PathResult(path, 0.0);
        }
        Map<String, Double> distances = new HashMap<>();//记录从起点到每个点的最短距离
        Map<String, String> previousNodes = new HashMap<>();//记录每个点的前驱，用于回溯路径

        //优先级队列，按距离大小排序
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(Comparator.comparingDouble(NodeDistance::getDistance));

        for (String node : adjacencyList.keySet()){
            distances.put(node, Double.POSITIVE_INFINITY);
        }

        distances.put(startNode, 0.0);
        pq.add(new NodeDistance(startNode,0.0));

        while(!pq.isEmpty()){
            NodeDistance current = pq.poll();
            String u = current.getNodeId();

            if(u.equals((endNode))){
                break;
            }

            if (current.getDistance() > distances.get(u)){
                continue;
            }

            for (Edge edge : adjacencyList.getOrDefault(u,new ArrayList<>())){
                String v = edge.getTargetNodeId();
                double weight = edge.getWeight();
                double newDist = distances.get(u) + weight;

                if (newDist < distances.get(v)) {
                    distances.put(v, newDist);
                    previousNodes.put(v, u); // 记录 v 是从 u 走过来的
                    pq.add(new NodeDistance(v, newDist));
                }
            }
        }
        return reconstructPath(previousNodes,distances,startNode,endNode);
    }

    private PathResult reconstructPath(Map<String, String> prev, Map<String, Double> dists, String start, String end) {
        List<String> path = new LinkedList<>();

        // 如果终点距离还是无穷大，说明不连通
        if (!dists.containsKey(end) || dists.get(end) == Double.POSITIVE_INFINITY) {
            return new PathResult(path, Double.POSITIVE_INFINITY);
        }

        // 从终点往回找
        for (String at = end; at != null; at = prev.get(at)) {
            path.add(at);
        }
        Collections.reverse(path); // 翻转得到：起点 -> ... -> 终点

        return new PathResult(path, dists.get(end));
    }

    private static class NodeDistance {
        private String nodeId;
        private double distance;

        public NodeDistance(String nodeId, double distance){
            this.distance = distance;
            this.nodeId = nodeId;
        }

        public String getNodeId(){
            return nodeId;
        }

        public double getDistance(){
            return distance;
        }
    }

    public PathResult getPathThrough(String[] nodes) {
        List<String> fullPath = new ArrayList<>();
        double totalDistance = 0.0;

        for (int i = 0; i < nodes.length - 1; i++) {
            PathResult part = getShortestPath(nodes[i], nodes[i + 1]);

            if (part.getTotalDistance() == Double.POSITIVE_INFINITY) {
                return new PathResult(new ArrayList<>(), Double.POSITIVE_INFINITY);
            }

            if (i == 0) {
                fullPath.addAll(part.getPath());
            } else {
                // 避免重复加入中间点
                fullPath.addAll(part.getPath().subList(1, part.getPath().size()));
            }

            totalDistance += part.getTotalDistance();
        }

        return new PathResult(fullPath, totalDistance);
    }
}
