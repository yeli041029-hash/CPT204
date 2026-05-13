import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InspectionSystem {
    private static final int RUN_TIMES = 3;
    private static final int TOP_COUNT = 10;

    private List<String> datasetNames;
    private List<String> filePaths;
    private List<Sorter> sorters;
    private Sorter finalSorter;

    public InspectionSystem() {
        this.datasetNames = Arrays.asList(
                "Dataset A",
                "Dataset B",
                "Dataset C"
        );

        this.filePaths = Arrays.asList(
                "data/candidates_A.csv",
                "data/candidates_B.csv",
                "data/candidates_C.csv"
        );

        this.sorters = Arrays.asList(
                new BubbleSort(),
                new QuickSort(),
                new MergeSort()
        );

        this.finalSorter = new MergeSort();
    }

    public void runSystem() {
        System.out.println("=== Task A: Sorting Algorithm ===");
        runTaskA();

        System.out.println("=== Task C: Integrated Application ===");
        Map<String, ArrayList<Location>> selectedTargets = prepareSelectedTargets();
        printSelectedTargets(selectedTargets);

        System.out.println("=== Task B: Graph Algorithm ===");
        runTaskB(selectedTargets);
    }

    public void runTaskAOnly() {
        System.out.println("=== Task A: Sorting Algorithm ===");
        runTaskA();
    }

    private void runTaskA() {
        for (int i = 0; i < datasetNames.size(); i++) {
            String datasetName = datasetNames.get(i);
            String filePath = filePaths.get(i);

            System.out.println("======================================");
            System.out.println(datasetName);
            System.out.println("======================================");

            for (Sorter sorter : sorters) {
                SortingResult result = runSortingTest(datasetName, filePath, sorter);
                printResult(result);
            }

            System.out.println();
        }
    }

    private Map<String, ArrayList<Location>> prepareSelectedTargets() {
        Map<String, ArrayList<Location>> selectedTargets = new LinkedHashMap<>();

        for (int i = 0; i < datasetNames.size(); i++) {
            String datasetName = datasetNames.get(i);
            String filePath = filePaths.get(i);
            ArrayList<Location> locations = CSVReader.readCandidateFile(filePath);

            finalSorter.sort(locations);
            selectedTargets.put(datasetName, extractTopLocations(locations));
        }

        return selectedTargets;
    }

    private void printSelectedTargets(Map<String, ArrayList<Location>> selectedTargets) {
        System.out.println("Final sorter used for integrated system: " + finalSorter.getName());

        for (Map.Entry<String, ArrayList<Location>> entry : selectedTargets.entrySet()) {
            System.out.print(entry.getKey() + " Top 10: ");

            for (Location location : entry.getValue()) {
                System.out.print(location + " ");
            }

            System.out.println();
        }

        System.out.println();
    }

    private void runTaskB(Map<String, ArrayList<Location>> selectedTargets) {
        Graph graph = new Graph();
        loadPaths(graph, "data/paths.csv");

        ArrayList<InspectionCase> inspectionCases = buildInspectionCases(selectedTargets);

        for (InspectionCase inspectionCase : inspectionCases) {
            printInspectionCase(inspectionCase);

            String[] nodes = inspectionCase.getNodes();
            PathResult result;

            if (nodes.length == 2) {
                result = graph.getShortestPath(nodes[0], nodes[1]);
            } else {
                result = graph.getPathThrough(nodes);
            }

            printResult(inspectionCase.getCaseName(), result);
        }
    }

    private ArrayList<InspectionCase> buildInspectionCases(Map<String, ArrayList<Location>> selectedTargets) {
        ArrayList<Location> datasetATargets = selectedTargets.get("Dataset A");
        ArrayList<Location> datasetBTargets = selectedTargets.get("Dataset B");
        ArrayList<Location> datasetCTargets = selectedTargets.get("Dataset C");

        if (datasetATargets == null || datasetATargets.size() < TOP_COUNT
                || datasetBTargets == null || datasetBTargets.size() < TOP_COUNT
                || datasetCTargets == null || datasetCTargets.size() < TOP_COUNT) {
            throw new IllegalStateException("Each dataset must provide at least 10 selected targets.");
        }

        String a1 = datasetATargets.get(0).getLocationId();
        String a10 = datasetATargets.get(9).getLocationId();
        String b1 = datasetBTargets.get(0).getLocationId();
        String b5 = datasetBTargets.get(4).getLocationId();
        String c1 = datasetCTargets.get(0).getLocationId();
        String c5 = datasetCTargets.get(4).getLocationId();

        ArrayList<InspectionCase> inspectionCases = new ArrayList<>();
        inspectionCases.add(new InspectionCase("Case 1", new String[]{a1, a1}));
        inspectionCases.add(new InspectionCase("Case 2", new String[]{a1, a10}));
        inspectionCases.add(new InspectionCase("Case 3", new String[]{a1, b5, b1}));
        inspectionCases.add(new InspectionCase("Case 4", new String[]{a1, b5, c5, c1}));

        return inspectionCases;
    }

    private ArrayList<Location> extractTopLocations(ArrayList<Location> locations) {
        ArrayList<Location> top10 = new ArrayList<>();

        for (int i = 0; i < TOP_COUNT && i < locations.size(); i++) {
            top10.add(locations.get(i));
        }

        return top10;
    }

    private SortingResult runSortingTest(String datasetName, String filePath, Sorter sorter) {
        long totalTime = 0;
        ArrayList<Location> finalSortedList = null;

        for (int i = 0; i < RUN_TIMES; i++) {
            ArrayList<Location> locations = CSVReader.readCandidateFile(filePath);
            long startTime = System.nanoTime();
            sorter.sort(locations);
            long endTime = System.nanoTime();

            totalTime += (endTime - startTime);

            if (i == RUN_TIMES - 1) {
                finalSortedList = locations;
            }
        }

        double averageTimeMs = totalTime / (double) RUN_TIMES / 1_000_000.0;
        ArrayList<Location> top10 = new ArrayList<>();

        for (int i = 0; i < 10 && i < finalSortedList.size(); i++) {
            top10.add(finalSortedList.get(i));
        }

        return new SortingResult(datasetName, sorter.getName(), averageTimeMs, top10);
    }

    private void printResult(SortingResult result) {
        System.out.println("Algorithm: " + result.getAlgorithmName());
        System.out.printf("Average Time: %.4f ms%n", result.getAverageTimeMs());
        System.out.print("Top 10 Locations: ");

        for (Location location : result.getTop10Locations()) {
            System.out.print(location + " ");
        }

        System.out.println();
        System.out.println();
    }

    private void loadPaths(Graph graph, String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String from = parts[0];
                String to = parts[1];
                double weight = Double.parseDouble(parts[2]);
                graph.addEdge(from, to, weight);
            }
        } catch (IOException e) {
            System.err.println("Error reading " + filename + ": " + e.getMessage());
        }
    }

    private void printInspectionCase(InspectionCase inspectionCase) {
        System.out.println("Case Name: " + inspectionCase.getCaseName());
        System.out.println("Starting Node: " + inspectionCase.getStartingNode());
        System.out.println("Destination Node: " + inspectionCase.getDestinationNode());

        List<String> requiredWaypoints = inspectionCase.getRequiredWaypoints();

        if (requiredWaypoints.isEmpty()) {
            System.out.println("Required Waypoint: None");
        } else {
            System.out.println("Required Waypoint: " + String.join(" -> ", requiredWaypoints));
        }
    }

    private void printResult(String caseName, PathResult result) {
        System.out.println(caseName + " Result:");
        System.out.println("Path: " + String.join(" -> ", result.getPath()));
        System.out.println("Total Cost: " + result.getTotalDistance());
        System.out.println();
    }
}
