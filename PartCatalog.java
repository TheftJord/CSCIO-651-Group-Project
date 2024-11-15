import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartCatalog {
    private BPlusTree partTree;

    public PartCatalog() {
        this.partTree = new BPlusTree();
    }

    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader("partfile.txt"))) {
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                // Trim to remove any leading/trailing whitespace
                line = line.trim();

                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                // Split the line into parts
                String[] partData = line.split(",");

                // at least 2 elements (partId and description)
                if (partData.length >= 2) {
                    // Trim to remove extra whitespace
                    String partId = partData[0].trim();
                    String description = partData[1].trim();

                    // Part object to insert into the B+ tree
                    Part part = new Part(partId, description);
                    partTree.insert(part);
                }
            }

            System.out.println("Successfully loaded parts from " + filename);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}