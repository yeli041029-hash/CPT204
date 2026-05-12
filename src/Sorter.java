//给 Bubble Sort、Quick Sort、Merge Sort 提供一个统一的“排序算法接口”，
// 并且把要求的统一排序规则写在这里，避免三个排序类各写一遍，减少出错。


import java.util.ArrayList;

public interface Sorter {
    //从 CSV 文件里读取 1000 个地点，然后放到ArrayList中
    void sort(ArrayList<Location> locations);

    //判断a这个地点是否应该排在b这个地点前面
    default boolean shouldComeBefore(Location a, Location b) {
        if (a.getPriorityScore() > b.getPriorityScore()) {
            return true;//a 应该在 b 前面
        } else if (a.getPriorityScore() < b.getPriorityScore()) {
            return false;
        } else { //a 和 b 的 priority_score 相等
            return a.getLocationId().compareTo(b.getLocationId()) < 0; //如果 priority_score 相同，则按照 location_id 升序排列
        }
    }

    default boolean shouldSwap(Location a, Location b) { //a 和 b 是否需要交换位置（主要给 Bubble Sort ）
        return !shouldComeBefore(a, b);//shouldSwap(a, b) 返回 true，表示需要交换
    }

    String getName();
}