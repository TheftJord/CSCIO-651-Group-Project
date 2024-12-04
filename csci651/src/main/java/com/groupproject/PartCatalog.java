package com.groupproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PartCatalog {
    private static BPlusTree partTree;

    @SuppressWarnings("static-access")
    public PartCatalog() {
        this.partTree = new BPlusTree();
    }

    public BPlusTree getCatalog() {
        return partTree;
    }

    
    //------------------------------------------------------------Flat File to Data Structure----------------------------------------------------------------------

    @SuppressWarnings("static-access")
    public static void loadFromFile(String filename) {

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            System.out.println("loading from file");
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                // Split the line into parts
                String[] partData = new String[2];
                partData[0] = line.substring(0, 7);
                partData[1] = line.substring(15);

                if (partData.length >= 2) {
                    // Trim to remove extra whitespace
                    String partId = partData[0].trim();
                    String description = partData[1].trim();

                    // Insert into the B+ tree
                    Part part = new Part(partId, description);
                    partTree.insert(part);
                }
            }

            System.out.println("Successfully loaded parts from " + filename);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }


    //----------------------------------------------------------Data Structure to Flat File--------------------------------------------------------------

    /** +}
     * Grabs filename
     * takes tree as arraylist 
     * takes each line and converts it to string then saves to desired file
     * @param filename
     * @throws FileNotFoundException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static void dataStructureToFile(String filename) throws FileNotFoundException, IOException{

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){ //makes file writer

            //grabs data structure
            ArrayList<String> converter = BPlusTree.printTreeDeformated(); //grabs data structure

            //data structure to flat file converter
            for (String temp:converter){ //loops through entire tree
                writer.write(temp); //writes string to file
            }
        }
    }

/*     public static void main(String[] args) {
        PartCatalog catalog = new PartCatalog();
        String filename = "/Users/sana/CSCI 651/bTreeFinal/src/partfile.txt";
        PartCatalog.loadFromFile(filename);
        System.out.println("File information loaded successfully.");
    } */
}
