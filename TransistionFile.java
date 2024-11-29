import java.io.*;
import java.util.*;

public class TransistionFile {



   public void dataStructureToFlatFile(BPlusTreeNode node, BufferedWriter writer) throws IOException {
        if (node == null) return;

        // Write current node's keys
        writer.write(node.isLeaf ? "LEAF:" : "NODE:");
        for (int key : node.keys) {
            writer.write(key + " ");
        }
        writer.newLine();

        // Recursively write children if not a leaf
        if (!node.isLeaf) {
            for (BPlusTreeNode child : node.children) {
                dataStructureToFlatFile(child, writer);
            }
        }
    }

    public void dataStructureToFlatFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            dataStructureToFlatFile(root, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Recursive method to reconstruct the B+-tree from a flat file
    public BPlusTreeNode flatFileToDataStructure(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        if (line == null) return null;

        boolean isLeaf = line.startsWith("LEAF:");
        BPlusTreeNode node = new BPlusTreeNode(isLeaf);
        String[] parts = line.split(":")[1].trim().split(" ");

        // Add keys to the node
        for (String key : parts) {
            if (!key.isEmpty()) {
                node.keys.add(Integer.parseInt(key));
            }
        }

        // If it's not a leaf, recursively add children
        if (!isLeaf) {
            while (reader.ready()) {
                BPlusTreeNode child = flatFileToDataStructure(reader);
                if (child == null) break;
                node.children.add(child);
            }
        }
        return node;
    }

    public void flatFileToDataStructure(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            root = flatFileToDataStructure(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
