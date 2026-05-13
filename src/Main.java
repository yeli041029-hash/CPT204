/*
 * Main is the entry point for the full system.
 * Running this class will execute Task A, Task C, and Task B in order.
 */
public class Main {
    /*
     * This method only starts the system.
     * The actual workflow is handled by InspectionSystem.
     */
    public static void main(String[] args) {
        InspectionSystem inspectionSystem = new InspectionSystem();
        inspectionSystem.runSystem();
    }
}
