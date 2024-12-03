import java.io.*;
import java.util.*;

public class TransitionFile {

    // Inner class representing a B+-tree node
    static class BPlusTreeNode {
        List<Integer> keys;

        public BPlusTreeNode() {
            this.keys = new ArrayList<>();
        }

        public void addKey(int key) {
            keys.add(key);
        }

        @Override
        public String toString() {
            return "BPlusTreeNode{" +
                    "keys=" + keys +
                    '}';
        }
    }

    public TransitionFile() {
    }

    // Converts B+-tree node to a flat file (keys separated by spaces)
    public void dataStructureToFlatFile(BPlusTreeNode node, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int key : node.keys) {
                writer.write(key + " ");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Converts a flat file to a B+-tree node
    public BPlusTreeNode flatFileToDataStructure(String filename) {
        BPlusTreeNode node = new BPlusTreeNode();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            if (line != null) {
                String[] keys = line.trim().split("\\s+");
                for (String key : keys) {
                    node.addKey(Integer.parseInt(key));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return node;
    }
}
//Psudo code
/** List<Row> inRows; //input to the algo
 List<Row> pendingRows = new LinkedList<>();
 Map<String, Row> depthToRows = new HashMap<>();

 pendingRows.addAll(inRows);
 while (!pendingRows.isEmpty()){
 for(Row row: inRows){
 depthToRows.put(row.getDepth(), row);
 //find the parent depth object
 String[] arr = row.getDepth().split(".");
 if(arr.length > 1){
 String parentDepth = String.join(".", Arrays.copyOfRange(arr, 0, arr.length - 1));
 if(null != depthToRows.get(parentDepth)){
 depthToRows.get(parentDepth).getSubRows().add(row);
 pendingRows.remove(row);
 }
 }
 }
 } **/
