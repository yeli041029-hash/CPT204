import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Main 类是程序的入口。
 *
 * 这个类主要完成 Task A 的整体流程：
 * 1. 读取三个候选地点数据集
 * 2. 分别使用 Bubble Sort、Quick Sort、Merge Sort 排序
 * 3. 测量每个算法的平均运行时间
 * 4. 输出每个数据集的 Top 10 地点
 */
public class Main {

    /*
     * 每个排序算法运行的次数。
     * 这里设置为 3 次，然后计算平均运行时间，
     * 这样可以减少单次运行带来的误差。
     */
    private static final int RUN_TIMES = 3;

    public static void main(String[] args) {

        /*
         * 保存三个数据集的名称。
         * 这些名称主要用于后面打印结果。
         */
        List<String> datasetNames = Arrays.asList(
                "Dataset A",
                "Dataset B",
                "Dataset C"
        );

        /*
         * 保存三个 CSV 文件的路径。
         * 注意：这些路径要和你项目中的 data 文件夹位置一致。
         */
        List<String> filePaths = Arrays.asList(
                "data/candidates_A.csv",
                "data/candidates_B.csv",
                "data/candidates_C.csv"
        );

        /*
         * 创建三个排序算法对象。
         * 因为它们都实现了 Sorter 接口，
         * 所以可以统一放进 List<Sorter> 里面。
         */
        List<Sorter> sorters = Arrays.asList(
                new BubbleSort(),
                new QuickSort(),
                new MergeSort()
        );

        /*
         * 外层循环遍历三个数据集。
         */
        for (int i = 0; i < datasetNames.size(); i++) {

            String datasetName = datasetNames.get(i);
            String filePath = filePaths.get(i);

            System.out.println("======================================");
            System.out.println(datasetName);
            System.out.println("======================================");

            /*
             * 内层循环遍历三个排序算法。
             * 每个算法都会在当前数据集上运行一次实验。
             */
            for (Sorter sorter : sorters) {
                SortingResult result = runSortingTest(datasetName, filePath, sorter);
                printResult(result);
            }

            System.out.println();
        }
    }

    /*
     * runSortingTest 方法用于执行一次完整的排序实验。
     *
     * 参数：
     * datasetName：数据集名称，例如 Dataset A
     * filePath：CSV 文件路径
     * sorter：当前使用的排序算法
     *
     * 返回值：
     * SortingResult，保存平均时间和 Top 10 地点
     */
    private static SortingResult runSortingTest(String datasetName, String filePath, Sorter sorter) {

        // 用来累计多次运行的总时间，单位是纳秒
        long totalTime = 0;

        // 保存最后一次排序后的列表，用来提取 Top 10
        ArrayList<Location> finalSortedList = null;

        /*
         * 每个算法运行 RUN_TIMES 次。
         * 每次都重新从 CSV 读取数据，保证排序前的数据是一样的。
         */
        for (int i = 0; i < RUN_TIMES; i++) {

            // 重新读取原始数据，避免使用已经排好序的列表
            ArrayList<Location> locations = CSVReader.readCandidateFile(filePath);

            // 记录排序开始时间
            long startTime = System.nanoTime();

            // 执行排序
            sorter.sort(locations);

            // 记录排序结束时间
            long endTime = System.nanoTime();

            // 累加本次排序耗时
            totalTime += (endTime - startTime);

            /*
             * 保存最后一次排序结果。
             * 后面会从这个列表中取前 10 个地点。
             */
            if (i == RUN_TIMES - 1) {
                finalSortedList = locations;
            }
        }

        /*
         * 计算平均运行时间。
         * System.nanoTime() 得到的是纳秒，
         * 除以 1,000,000 后转换成毫秒。
         */
        double averageTimeMs = totalTime / (double) RUN_TIMES / 1_000_000.0;

        /*
         * 提取排序后的前 10 个地点。
         */
        ArrayList<Location> top10 = new ArrayList<>();

        for (int i = 0; i < 10 && i < finalSortedList.size(); i++) {
            top10.add(finalSortedList.get(i));
        }

        /*
         * 把本次实验结果封装成 SortingResult 对象并返回。
         */
        return new SortingResult(datasetName, sorter.getName(), averageTimeMs, top10);
    }

    /*
     * printResult 方法用于打印排序实验结果。
     */
    private static void printResult(SortingResult result) {

        System.out.println("Algorithm: " + result.getAlgorithmName());
        System.out.printf("Average Time: %.4f ms%n", result.getAverageTimeMs());

        System.out.print("Top 10 Locations: ");

        /*
         * 输出 Top 10 地点的 location_id。
         * 如果你想检查分数，可以把 location.getLocationId()
         * 改成 location.toString()。
         */
        for (Location location : result.getTop10Locations()) {
            System.out.print(location + " ");
        }

        System.out.println();
        System.out.println();
    }
}