import java.util.ArrayList;

/*
 * SortingResult 类用于保存一次排序实验的结果。
 *
 * 它主要保存以下信息：
 * 1. 数据集名称，例如 Dataset A
 * 2. 使用的排序算法名称，例如 Bubble Sort
 * 3. 平均运行时间
 * 4. 排序后的 Top 10 地点
 */
public class SortingResult {

    // 数据集名称，例如 Dataset A、Dataset B、Dataset C
    private String datasetName;

    // 排序算法名称，例如 Bubble Sort、Quick Sort、Merge Sort
    private String algorithmName;

    // 多次运行后的平均时间，单位是毫秒
    private double averageTimeMs;

    // 当前数据集排序后的前 10 个地点
    private ArrayList<Location> top10Locations;

    /*
     * 构造方法。
     * 创建 SortingResult 对象时，需要传入完整的排序结果信息。
     */
    public SortingResult(String datasetName, String algorithmName,
                         double averageTimeMs, ArrayList<Location> top10Locations) {
        this.datasetName = datasetName;
        this.algorithmName = algorithmName;
        this.averageTimeMs = averageTimeMs;
        this.top10Locations = top10Locations;
    }

    /*
     * 返回数据集名称。
     */
    public String getDatasetName() {
        return datasetName;
    }

    /*
     * 返回排序算法名称。
     */
    public String getAlgorithmName() {
        return algorithmName;
    }

    /*
     * 返回平均运行时间。
     */
    public double getAverageTimeMs() {
        return averageTimeMs;
    }

    /*
     * 返回 Top 10 地点列表。
     */
    public ArrayList<Location> getTop10Locations() {
        return top10Locations;
    }
}