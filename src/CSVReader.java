import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * CSVReader is responsible for reading candidate location files.
 * In this project, it is mainly used in Task A to convert each CSV row into a Location object.
 */
public class CSVReader {

    /*
     * Reads one candidates file, such as data/candidates_A.csv.
     * The returned list will later be passed to different sorting algorithms.
     */
    public static ArrayList<Location> readCandidateFile(String filePath) {

        // Stores all locations read from the file
        ArrayList<Location> locations = new ArrayList<>();

        // Read the file line by line
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            boolean isFirstLine = true;

            // Keep reading until the end of the file
            while ((line = br.readLine()) != null) {

                // Skip blank lines so they do not affect the result
                if (line.trim().isEmpty()) {
                    continue;
                }

                // If the first line is a header, do not treat it as data
                if (isFirstLine) {
                    isFirstLine = false;

                    if (line.toLowerCase().contains("location")) {
                        continue;
                    }
                }

                // Each row in the candidates file is separated by commas
                String[] parts = line.split(",");

                // Normally there should be at least two columns:
                // location_id and priority_score
                if (parts.length >= 2) {
                    String locationId = parts[0].trim();

                    // Convert the score from text to double for later comparisons
                    double priorityScore = Double.parseDouble(parts[1].trim());

                    // Create a Location object for each row and add it to the list
                    locations.add(new Location(locationId, priorityScore));
                }
            }

        } catch (IOException e) {
            // Print an error message if the file path is incorrect
            System.out.println("Error reading file: " + filePath);
            e.printStackTrace();
        }

        // Return all location data from the current file
        return locations;
    }
}
