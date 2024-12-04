package com.groupproject;

import java.util.ArrayList;

public class test {
  @SuppressWarnings("static-access")
  public static void main(String[] args) {
    PartCatalog A = new PartCatalog();
    PartCatalog.loadFromFile("csci651\\src\\main\\resources\\com\\groupproject\\partfile.txt");

    BPlusTree catalog = A.getCatalog();
    int mergeCount = catalog.getMargeCount();
    int splitCount = catalog.getSplitCount();
    int treeHeight = catalog.getTreeHeight();

    System.out.println("MergeCount= " + mergeCount + " SplitCount= " + splitCount + " treeHeight= " + treeHeight);

    System.out.println(catalog.search("AAA-133").getDescription());

    ArrayList<Part> list = catalog.displayNext10(catalog.search("HIU-699"));

    catalog.printTree();

    for (Part item : list) {
      System.out.print(item.getPartId() + " ");
    }

  }
}