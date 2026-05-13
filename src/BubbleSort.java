import java.util.ArrayList;

/*
 * BubbleSort is the implementation of bubble sort.
 * This version works directly on the original list,
 * and the ranking rule is handled by the default methods in Sorter.
 */
public class BubbleSort implements Sorter {

    /*
     * Bubble sort compares adjacent elements round by round.
     * If the order is not correct, it swaps them.
     */
    @Override
    public void sort(ArrayList<Location> locations) {

        int n = locations.size();

        // The outer loop controls how many rounds are needed
        for (int i = 0; i < n - 1; i++) {

            // If no swap happens in this round, the list is already sorted
            boolean swapped = false;

            // Each round compares neighboring elements in the current range
            for (int j = 0; j < n - i - 1; j++) {

                Location current = locations.get(j);
                Location next = locations.get(j + 1);

                // Swap if the current order does not match the required rule
                if (!shouldComeBefore(current, next)) {
                    locations.set(j, next);
                    locations.set(j + 1, current);
                    swapped = true;
                }
            }

            // Early stopping helps when the dataset is already nearly sorted
            if (!swapped) {
                break;
            }
        }
    }

    /*
     * Returns the algorithm name for printing.
     */
    @Override
    public String getName() {
        return "Bubble Sort";
    }
}
