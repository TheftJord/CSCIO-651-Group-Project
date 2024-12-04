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

    System.out.println(catalog.infoToString());

    ArrayList<Part> list = catalog.displayNext10(catalog.search("HIU-699"));

    for (Part item : list) {
      System.out.print(item.getPartId() + " ");
    }
    System.out.println("");
    /*
     * catalog.delete("MLI-309");
     * catalog.delete("MLI-335");
     * catalog.delete("MLI-384");
     * catalog.delete("MLI-449");
     * catalog.delete("MLI-479");
     * catalog.delete("MLI-578");
     * catalog.delete("MLI-606");
     * catalog.delete("MLI-614");
     * catalog.delete("MLI-713");
     * catalog.delete("MLI-717");
     * catalog.delete("MLI-780");
     * catalog.delete("MLI-849");
     * catalog.delete("MLI-907");
     * catalog.delete("MLI-497");
     */

    System.out.println(catalog.infoToString());
    catalog.printTree();

  }
}