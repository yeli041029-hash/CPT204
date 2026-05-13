import java.util.ArrayList;

/*
 * SortingResult is a simple data class.
 * It stores the result of one dataset under one sorting algorithm.
 */
public class SortingResult {

    // Which dataset this result belongs to
    private String datasetName;

    // Which sorting algorithm produced this result
    private String algorithmName;

    // Average running time after multiple runs, in milliseconds
    private double averageTimeMs;

    // Top 10 locations after sorting
    private ArrayList<Location> top10Locations;

    /*
     * Creates one complete sorting result object.
     */
    public SortingResult(String datasetName, String algorithmName,
                         double averageTimeMs, ArrayList<Location> top10Locations) {
        this.datasetName = datasetName;
        this.algorithmName = algorithmName;
        this.averageTimeMs = averageTimeMs;
        this.top10Locations = top10Locations;
    }

    /*
     * Returns the dataset name.
     */
    public String getDatasetName() {
        return datasetName;
    }

    /*
     * Returns the sorting algorithm name.
     */
    public String getAlgorithmName() {
        return algorithmName;
    }

    /*
     * Returns the average running time.
     */
    public double getAverageTimeMs() {
        return averageTimeMs;
    }

    /*
     * Returns the Top 10 location list.
     */
    public ArrayList<Location> getTop10Locations() {
        return top10Locations;
    }
}
