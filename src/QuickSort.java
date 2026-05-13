import java.util.ArrayList;

/*
 * QuickSort is the implementation of quick sort.
 * It works by repeatedly choosing a pivot
 * and dividing the list into left and right parts.
 */
public class QuickSort implements Sorter {

    /*
     * This is the public entry point.
     * The actual recursive logic is in quickSort().
     */
    @Override
    public void sort(ArrayList<Location> locations) {
        quickSort(locations, 0, locations.size() - 1);
    }

    /*
     * Recursively sorts the section from low to high.
     */
    private void quickSort(ArrayList<Location> locations, int low, int high) {

        // Continue only if this section has at least two elements
        if (low < high) {
            int pivotIndex = partition(locations, low, high);

            // Sort the left and right parts around the pivot
            quickSort(locations, low, pivotIndex - 1);
            quickSort(locations, pivotIndex + 1, high);
        }
    }

    /*
     * This version uses the last element as the pivot.
     * Elements that should come before the pivot are moved to the left side.
     */
    private int partition(ArrayList<Location> locations, int low, int high) {

        Location pivot = locations.get(high);

        // i marks the end of the left valid section
        int i = low - 1;

        // Check each element and move it left if needed
        for (int j = low; j < high; j++) {
            if (shouldComeBefore(locations.get(j), pivot)) {
                i++;
                swap(locations, i, j);
            }
        }

        // Put the pivot into its correct final position
        swap(locations, i + 1, high);

        return i + 1;
    }

    /*
     * Swaps two elements in the list.
     */
    private void swap(ArrayList<Location> locations, int i, int j) {
        Location temp = locations.get(i);
        locations.set(i, locations.get(j));
        locations.set(j, temp);
    }

    /*
     * Returns the algorithm name for printing.
     */
    @Override
    public String getName() {
        return "Quick Sort";
    }
}
