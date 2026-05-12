public class Location {
    private String locationId;
    private double priorityScore;

    public Location(String locationId, double priorityScore){
        this.locationId=locationId;
        this.priorityScore=priorityScore;
    }

    public String getLocationId() {
        return locationId;
    }

    public double getPriorityScore() {
        return priorityScore;
    }

    @Override
    public String toString(){
        return locationId+ "(" + priorityScore + ")";
    }
}
