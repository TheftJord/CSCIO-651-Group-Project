package com.groupproject;

public class test {
  public static void main(String[] args) {
    PartCatalog A = new PartCatalog();
    A.loadFromFile("csci651\\src\\main\\resources\\com\\groupproject\\partfile.txt");

    BPlusTree catalog = A.getCatalog();
    int mergeCount = catalog.getMargeCount();
    int splitCount = catalog.getSplitCount();
    int treeHeight = catalog.getTreeHeight();

    System.out.println("MergeCount= " + mergeCount + " SplitCount= " + splitCount + " treeHeight= " + treeHeight);

  }
}
