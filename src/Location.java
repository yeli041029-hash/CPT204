/*
 * Location represents one candidate location.
 * Each location only needs an ID and a priority score.
 */
public class Location {
    private String locationId;
    private double priorityScore;

    /*
     * Creates a location object that will be used later in sorting.
     */
    public Location(String locationId, double priorityScore){
        this.locationId=locationId;
        this.priorityScore=priorityScore;
    }

    /*
     * Returns the location ID.
     * This is the main getter used in Task A.
     */
    public String getLocationId() {
        return locationId;
    }

    /*
     * This method is kept to stay compatible with the original Task B code.
     */
    public String getId() {
        return locationId;
    }

    /*
     * Returns the priority score of this location.
     */
    public double getPriorityScore() {
        return priorityScore;
    }

    /*
     * Makes it easier to print a location as "ID(score)" in the console.
     */
    @Override
    public String toString(){
        return locationId+ "(" + priorityScore + ")";
    }
}
