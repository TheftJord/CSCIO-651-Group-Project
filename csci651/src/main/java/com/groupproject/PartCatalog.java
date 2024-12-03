package com.groupproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartCatalog {
    private static BPlusTree partTree;
    
        public PartCatalog() {
            this.partTree = new BPlusTree();
        }
    
        public BPlusTree getCatalog() {
            return partTree;
        }
    
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
                        System.out.println("partID = " + partId + " description = " + description);
                        partTree.insert(part);
                }
            }

            System.out.println("Successfully loaded parts from " + filename);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        PartCatalog catalog = new PartCatalog();
        String filename = "/Users/sana/CSCI 651/bTreeFinal/src/partfile.txt";
        PartCatalog.loadFromFile(filename);
        System.out.println("File information loaded successfully.");
    }
}
