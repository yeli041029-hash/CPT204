import java.util.ArrayList;

/*
 * BubbleSort 类实现了 Sorter 接口。
 * 它使用冒泡排序对候选地点进行排序。
 *
 * 排序规则不是直接写在这里，
 * 而是使用 Sorter 接口中的 shouldComeBefore 方法：
 * 1. priority_score 高的排前面
 * 2. 如果分数相同，location_id 小的排前面
 */
public class BubbleSort implements Sorter {

    /*
     * sort 方法会直接修改传入的 locations 列表，
     * 使它按照项目要求完成排序。
     */
    @Override
    public void sort(ArrayList<Location> locations) {

        int n = locations.size();

        /*
         * 外层循环控制排序轮数。
         * 每完成一轮，当前范围内“应该排在后面”的元素会逐渐移动到后面。
         */
        for (int i = 0; i < n - 1; i++) {

            // 用来记录这一轮是否发生过交换
            boolean swapped = false;

            /*
             * 内层循环比较相邻的两个 Location。
             * 因为每一轮都会确定一个后面的元素，
             * 所以比较范围可以逐轮缩小。
             */
            for (int j = 0; j < n - i - 1; j++) {

                Location current = locations.get(j);
                Location next = locations.get(j + 1);

                /*
                 * 如果 current 不应该排在 next 前面，
                 * 就交换它们的位置。
                 */
                if (!shouldComeBefore(current, next)) {
                    locations.set(j, next);
                    locations.set(j + 1, current);
                    swapped = true;
                }
            }

            /*
             * 如果某一轮没有发生任何交换，
             * 说明列表已经排好序，可以提前结束。
             */
            if (!swapped) {
                break;
            }
        }
    }

    /*
     * 返回当前排序算法的名称。
     * 这个方法主要用于 Main.java 中打印实验结果。
     */
    @Override
    public String getName() {
        return "Bubble Sort";
    }
}