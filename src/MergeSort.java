import java.util.ArrayList;

/*
 * MergeSort is the implementation of merge sort.
 * This algorithm first splits the list and then merges sorted parts back together.
 */
public class MergeSort implements Sorter {

    /*
     * Public entry point.
     * It first gets a new sorted list and then copies the result back to the original list.
     */
    @Override
    public void sort(ArrayList<Location> locations) {
        ArrayList<Location> sorted = mergeSort(locations);

        for (int i = 0; i < locations.size(); i++) {
            locations.set(i, sorted.get(i));
        }
    }

    /*
     * Recursively splits the list until each part has 0 or 1 element.
     */
    private ArrayList<Location> mergeSort(ArrayList<Location> locations) {

        // A list with one element is already sorted
        if (locations.size() <= 1) {
            return locations;
        }

        // Split the list into left and right halves
        int middle = locations.size() / 2;
        ArrayList<Location> left = new ArrayList<>(locations.subList(0, middle));
        ArrayList<Location> right = new ArrayList<>(locations.subList(middle, locations.size()));

        left = mergeSort(left);
        right = mergeSort(right);

        // Merge the two sorted halves into one sorted list
        return merge(left, right);
    }

    /*
     * Merges two already sorted lists.
     */
    private ArrayList<Location> merge(ArrayList<Location> left, ArrayList<Location> right) {

        ArrayList<Location> result = new ArrayList<>();

        int i = 0;
        int j = 0;

        // Compare elements from both sides and add the correct one first
        while (i < left.size() && j < right.size()) {
            if (shouldComeBefore(left.get(i), right.get(j))) {
                result.add(left.get(i));
                i++;
            } else {
                result.add(right.get(j));
                j++;
            }
        }

        // Add any remaining elements from the left list
        while (i < left.size()) {
            result.add(left.get(i));
            i++;
        }

        // Add any remaining elements from the right list
        while (j < right.size()) {
            result.add(right.get(j));
            j++;
        }

        return result;
    }

    /*
     * Returns the algorithm name for printing.
     */
    @Override
    public String getName() {
        return "Merge Sort";
    }
}
