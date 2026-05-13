import java.util.ArrayList;

/*
 * Sorter is the shared interface for the three sorting algorithms.
 * Besides defining sort() and getName(),
 * it also keeps the common ranking rule as default methods
 * so the same logic does not need to be repeated in each class.
 */
public interface Sorter {
    /*
     * Sorts a list of locations.
     */
    void sort(ArrayList<Location> locations);

    /*
     * Shared comparison rule:
     * 1. Higher priorityScore comes first
     * 2. If the scores are equal, smaller locationId comes first
     */
    default boolean shouldComeBefore(Location a, Location b) {
        if (a.getPriorityScore() > b.getPriorityScore()) {
            return true;
        } else if (a.getPriorityScore() < b.getPriorityScore()) {
            return false;
        } else {
            return a.getLocationId().compareTo(b.getLocationId()) < 0;
        }
    }

    /*
     * This method is mainly used by Bubble Sort.
     * If a should not come before b, their positions should be swapped.
     */
    default boolean shouldSwap(Location a, Location b) {
        return !shouldComeBefore(a, b);
    }

    /*
     * Returns the algorithm name for printing results.
     */
    String getName();
}
