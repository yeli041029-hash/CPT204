import java.util.ArrayList;

/*
 * MergeSort 类实现了 Sorter 接口。
 * 它使用归并排序对候选地点进行排序。
 *
 * 排序规则统一使用 Sorter 接口中的 shouldComeBefore 方法：
 * 1. priority_score 高的排前面
 * 2. 如果分数相同，location_id 小的排前面
 */
public class MergeSort implements Sorter {

    /*
     * sort 方法是外部调用归并排序的入口。
     * 它会直接修改传入的 locations 列表。
     */
    @Override
    public void sort(ArrayList<Location> locations) {

        /*
         * mergeSort 方法会返回一个排好序的新列表。
         * 然后我们再把排好序的结果复制回原来的 locations 中。
         */
        ArrayList<Location> sorted = mergeSort(locations);

        for (int i = 0; i < locations.size(); i++) {
            locations.set(i, sorted.get(i));
        }
    }

    /*
     * mergeSort 是递归方法。
     * 它会不断把列表分成左右两部分，
     * 分别排序后再合并。
     */
    private ArrayList<Location> mergeSort(ArrayList<Location> locations) {

        /*
         * 如果列表中只有 0 个或 1 个元素，
         * 说明它本身已经是有序的，直接返回。
         */
        if (locations.size() <= 1) {
            return locations;
        }

        // 找到中间位置，用来把列表分成两半
        int middle = locations.size() / 2;

        // 左半部分
        ArrayList<Location> left = new ArrayList<>(locations.subList(0, middle));

        // 右半部分
        ArrayList<Location> right = new ArrayList<>(locations.subList(middle, locations.size()));

        // 递归排序左半部分
        left = mergeSort(left);

        // 递归排序右半部分
        right = mergeSort(right);

        // 合并两个已经排好序的列表
        return merge(left, right);
    }

    /*
     * merge 方法用于合并两个已经排好序的列表。
     * 合并后的结果仍然是有序的。
     */
    private ArrayList<Location> merge(ArrayList<Location> left, ArrayList<Location> right) {

        // 用来保存合并后的结果
        ArrayList<Location> result = new ArrayList<>();

        int i = 0; // 指向 left 当前比较的位置
        int j = 0; // 指向 right 当前比较的位置

        /*
         * 只要左右两个列表都还有元素，
         * 就比较它们当前的元素，选择更应该排在前面的那个加入 result。
         */
        while (i < left.size() && j < right.size()) {

            if (shouldComeBefore(left.get(i), right.get(j))) {
                result.add(left.get(i));
                i++;
            } else {
                result.add(right.get(j));
                j++;
            }
        }

        /*
         * 如果 left 还有剩余元素，
         * 直接加入 result。
         */
        while (i < left.size()) {
            result.add(left.get(i));
            i++;
        }

        /*
         * 如果 right 还有剩余元素，
         * 直接加入 result。
         */
        while (j < right.size()) {
            result.add(right.get(j));
            j++;
        }

        return result;
    }

    /*
     * 返回当前排序算法的名称。
     * 主要用于 Main.java 中打印实验结果。
     */
    @Override
    public String getName() {
        return "Merge Sort";
    }
}