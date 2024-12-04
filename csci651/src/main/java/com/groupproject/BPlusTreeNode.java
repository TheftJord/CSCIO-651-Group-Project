package com.groupproject;

import java.util.ArrayList;
import java.util.List;

public class BPlusTreeNode {
    private static final int MAX_KEYS = 4;
    private static final int MAX_RECORDS = 16;
    private static final int MIN_KEYS = 2;

    List<Part> keys;
    List<BPlusTreeNode> children;
    BPlusTreeNode parent;
    boolean isLeaf;
    BPlusTreeNode nextLeaf;

    public BPlusTreeNode(boolean isLeaf) {
        this.isLeaf = isLeaf;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
        this.parent = null;
        this.nextLeaf = null;
    }

    public boolean isFull() {
        return keys.size() >= (isLeaf ? MAX_RECORDS + 1 : MAX_KEYS + 1);
    }

    public boolean isunderflow() {
        return keys.size() < MIN_KEYS;
    }
}
