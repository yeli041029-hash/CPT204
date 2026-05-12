import java.util.ArrayList;

/*
 * QuickSort 类实现了 Sorter 接口。
 * 它使用快速排序对候选地点进行排序。
 *
 * 排序规则统一使用 Sorter 接口中的 shouldComeBefore 方法：
 * 1. priority_score 高的排前面
 * 2. 如果分数相同，location_id 小的排前面
 */
public class QuickSort implements Sorter {

    /*
     * sort 方法是外部调用快速排序的入口。
     * 它会直接修改传入的 locations 列表。
     */
    @Override
    public void sort(ArrayList<Location> locations) {
        quickSort(locations, 0, locations.size() - 1);
    }

    /*
     * quickSort 是递归方法。
     *
     * low 表示当前排序范围的起始下标。
     * high 表示当前排序范围的结束下标。
     */
    private void quickSort(ArrayList<Location> locations, int low, int high) {

        /*
         * 当 low < high 时，说明当前范围里至少有两个元素，
         * 还需要继续排序。
         */
        if (low < high) {

            /*
             * partition 会选择一个 pivot，
             * 并把比 pivot 更应该靠前的元素放到左边，
             * 其他元素放到右边。
             *
             * 返回值 pivotIndex 是 pivot 排好后的最终位置。
             */
            int pivotIndex = partition(locations, low, high);

            // 递归排序 pivot 左边的部分
            quickSort(locations, low, pivotIndex - 1);

            // 递归排序 pivot 右边的部分
            quickSort(locations, pivotIndex + 1, high);
        }
    }

    /*
     * partition 方法用于划分当前排序范围。
     *
     * 这里选择当前范围最后一个元素作为 pivot。
     */
    private int partition(ArrayList<Location> locations, int low, int high) {

        // 选择最后一个元素作为基准值 pivot
        Location pivot = locations.get(high);

        /*
         * i 用来记录“比 pivot 更应该靠前”的区域的最后一个位置。
         * 一开始还没有这样的元素，所以设为 low - 1。
         */
        int i = low - 1;

        /*
         * j 从 low 遍历到 high - 1。
         * 每次检查 locations[j] 是否应该排在 pivot 前面。
         */
        for (int j = low; j < high; j++) {

            /*
             * 如果当前元素应该排在 pivot 前面，
             * 就把它交换到左边区域。
             */
            if (shouldComeBefore(locations.get(j), pivot)) {
                i++;
                swap(locations, i, j);
            }
        }

        /*
         * 循环结束后，把 pivot 放到它最终应该在的位置。
         * pivot 左边的元素都应该排在 pivot 前面。
         */
        swap(locations, i + 1, high);

        // 返回 pivot 的最终下标
        return i + 1;
    }

    /*
     * 交换列表中两个位置的 Location 对象。
     */
    private void swap(ArrayList<Location> locations, int i, int j) {
        Location temp = locations.get(i);
        locations.set(i, locations.get(j));
        locations.set(j, temp);
    }

    /*
     * 返回当前排序算法的名称。
     * 主要用于 Main.java 中打印实验结果。
     */
    @Override
    public String getName() {
        return "Quick Sort";
    }
}