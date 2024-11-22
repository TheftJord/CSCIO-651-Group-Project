package com.groupproject;

public class BPlusTree {
    private BPlusTreeNode root;
    private int treeHeight;

    // Tracking
    private int splitCount = 0;
    private int mergeCount = 0;

    public BPlusTree() {
        root = new BPlusTreeNode(true);
        treeHeight = 1;
    }

    // Search method
    public Part search(String partId) {
        return searchInNode(root, new Part(partId, ""));
    }

    public int getSplitCount() {
        return splitCount;
    }

    public int getMargeCount() {
        return mergeCount;
    }

    public int getTreeHeight() {
        return treeHeight;
    }

    private Part searchInNode(BPlusTreeNode node, Part part) {
        if (node.isLeaf) {
            // Search in leaf node
            for (Part p : node.keys) {
                if (p.getPartId().equals(part.getPartId())) {
                    return p;
                }
            }
            return null;
        }

        // Navigate internal nodes
        for (int i = 0; i < node.keys.size(); i++) {
            if (part.compareTo(node.keys.get(i)) < 0) {
                return searchInNode(node.children.get(i), part);
            }
        }
        return searchInNode(node.children.get(node.children.size() - 1), part);
    }

    // Update method
    public boolean updatePartDescription(String partId, String newDescription) {
        Part part = search(partId);
        if (part != null) {
            part.setDescription(newDescription);
            return true;
        }
        return false;
    }

    // Insert method
    public void insert(Part part) {
        // If root is full, split it
        if (root.isFull()) {
            BPlusTreeNode newRoot = new BPlusTreeNode(false);
            newRoot.children.add(root);
            splitChild(newRoot, 0);
            root = newRoot;
            treeHeight++;
            splitCount++;
        }
        insertNonFull(root, part);
    }

    private void insertNonFull(BPlusTreeNode node, Part part) {
        if (node.isLeaf) {
            // Insert into leaf node
            insertIntoLeaf(node, part);
        } else {
            // Find appropriate child to insert into
            int index = 0;
            while (index < node.keys.size() && part.compareTo(node.keys.get(index)) > 0) {
                index++;
            }

            BPlusTreeNode child = node.children.get(index);

            // Split child if full
            if (child.isFull()) {
                splitChild(node, index);
                // Adjust index if needed after split
                if (part.compareTo(node.keys.get(index)) > 0) {
                    index++;
                }
            }
            insertNonFull(node.children.get(index), part);
        }
    }

    private void insertIntoLeaf(BPlusTreeNode leaf, Part part) {
        int insertIndex = 0;
        while (insertIndex < leaf.keys.size() && leaf.keys.get(insertIndex).compareTo(part) < 0) {
            insertIndex++;
        }
        leaf.keys.add(insertIndex, part);
    }

    // Split child method
    private void splitChild(BPlusTreeNode parent, int childIndex) {
        BPlusTreeNode child = parent.children.get(childIndex);
        BPlusTreeNode newChild = new BPlusTreeNode(child.isLeaf);

        // Move half the keys to the new node
        int midPoint = child.keys.size() / 2;
        newChild.keys.addAll(child.keys.subList(midPoint, child.keys.size()));
        child.keys.subList(midPoint, child.keys.size()).clear();

        // If not a leaf, move children as well
        if (!child.isLeaf) {
            newChild.children.addAll(child.children.subList(midPoint, child.children.size()));
            child.children.subList(midPoint, child.children.size()).clear();
        }

        // Insert middle key into parent
        parent.keys.add(childIndex, newChild.keys.get(0));
        parent.children.add(childIndex + 1, newChild);
    }

    // Delete method
    public boolean delete(String partId) {
        // Find the leaf node containing the part
        BPlusTreeNode leafNode = findLeafNode(root, partId);

        if (leafNode != null) {
            // Remove the part from the leaf node
            for (int i = 0; i < leafNode.keys.size(); i++) {
                if (leafNode.keys.get(i).getPartId().equals(partId)) {
                    leafNode.keys.remove(i);
                    return true;
                }
            }
        }

        return false;
    }

    private BPlusTreeNode findLeafNode(BPlusTreeNode node, String partId) {
        // If it's a leaf node, return it
        if (node.isLeaf) {
            return node;
        }

        // Navigate through internal nodes
        for (int i = 0; i < node.keys.size(); i++) {
            if (partId.compareTo(node.keys.get(i).getPartId()) < 0) {
                return findLeafNode(node.children.get(i), partId);
            }
        }

        // If we've gone through all keys, go to the last child
        return findLeafNode(node.children.get(node.children.size() - 1), partId);
    }
}
