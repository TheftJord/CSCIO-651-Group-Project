package com.groupproject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javafx.util.Pair;

public class BPlusTree {

    private static BPlusTreeNode root;
    private static int treeHeight;

    // Tracking
    private static int splitCount = 0;
    private static int mergeCount = 0;

    public BPlusTree() {
        root = new BPlusTreeNode(true);
        treeHeight = 1;
    }

    // ________________________________________________GETTERS_______________________________________________________________________
    /**
     * 
     * @return
     */
    public static int getSplitCount() {
        return splitCount;
    }

    public static int getMargeCount() {
        // oh Homie
        return mergeCount;
    }

    public static int getTreeHeight() {
        return treeHeight;
    }

    public static BPlusTreeNode getRoot() {
        return root;
    }

    // __________________________________________DISPLAY_INFORMATION_FUNCTIONALITY____________________________________________
    public static String infoToString() {
        String temp = "Split Count: " + getSplitCount() + ", Merge Count: " + getMargeCount() + ", Tree Height: "
                + getTreeHeight() + ", Root: " + getRoot().toString();
        return temp;
    }

    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    /// ___________________________________________________SEARCH_FUNCTIONALITY_______________________________________________________
    ///
    /**
     * search method
     * 
     * @param partId
     * @return
     */
    @SuppressWarnings("exports")
    public static Part search(String partId) {
        return searchInNode(root, new Part(partId, ""));
    }

    private static Part searchInNode(BPlusTreeNode node, Part part) {

        // find the appropriate leaf in which the part should be in
        BPlusTreeNode leaf = node.isLeaf ? node : findLeaf(node, part.getPartId());

        // Navigate internal nodes and if key was found return it
        for (Part key : leaf.keys) {
            if (key != null && part != null) {
                if (key.getPartId().equals(part.getPartId())) {
                    return key;
                }
            }
        }
        return null; // if part id wasnt found in the range return null
    }

    /**
     * 
     * @param node
     * @param partID
     * @return
     */
    // Find the appropriate leaf node
    public static BPlusTreeNode findLeaf(BPlusTreeNode node, String partID) {

        // if the node is already a leaf return it
        if (node.isLeaf)
            return node;

        // iterate through the nodes keys to find the appropriate index to search in
        int i = 0;
        while (i < node.keys.size() && partID.compareTo(node.keys.get(i).getPartId()) >= 0) {
            i++;
        }

        // use the index to find the child
        BPlusTreeNode child = node.children.get(i);

        // recursively call the search method to the appropriate child
        return findLeaf(child, partID);
    }

    /**
     * 
     * @param list
     * @param part
     * @return
     */
    private static int findIndex(List<Part> list, Part part) {
        int i = 0;
        // find the appropriate index to insert into in the leaf
        while (i < list.size() && part.compareTo(list.get(i)) > 0) {
            i++;
        }
        return i;
    }

    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    /// _____________________________________________DISPLAY_NEXT_10_FUNCTIONALITY_______________________________________________________
    /**
     * 
     * /**
     * 
     * @param part
     * @return
     */
    @SuppressWarnings("exports")
    public static ArrayList<Part> displayNext10(Part part) {

        ArrayList<Part> returnList = new ArrayList<>();

        // if leaf doesnt exist or the part doesnt exist in the leaf retrun empty list
        boolean partExist = search(part.getPartId()) != null;
        if (!partExist) {
            return returnList;
        }

        // initialize the leaf and the index of the part within it
        BPlusTreeNode leaf = findLeaf(root, part.getPartId());
        int index = leaf.keys.indexOf(part);

        while (returnList.size() < 10) {
            // Add remaining parts from the current node
            for (int i = index; i < leaf.keys.size() && returnList.size() < 10; i++) {
                returnList.add(leaf.keys.get(i));
            }

            if (returnList.size() < 10) {
                leaf = findNextLeafOnTheRight(leaf);
                if (leaf == null) { // No more leaf nodes available
                    break;
                }
                index = 0; // Start from the first key in the new leaf node
            }
        }

        return returnList;
    }

    /**
     * 
     * @param current
     * @return
     */
    private static BPlusTreeNode findLeftmostLeaf(BPlusTreeNode current) {

        if (current.isLeaf) {
            return current;
        }

        return findLeftmostLeaf(current.children.get(0));
    }

    /**
     * 
     * @param currentnode
     * @return
     */
    private static BPlusTreeNode findNextLeafOnTheRight(BPlusTreeNode currentnode) {
        if (currentnode.parent == null) {
            return null; // Root node has no siblings
        }

        int currentnodeIndex = currentnode.parent.children.indexOf(currentnode);

        // Try to find the next sibling
        while (currentnode.parent != null) {
            if (currentnodeIndex < currentnode.parent.children.size() - 1) {
                return findLeftmostLeaf(currentnode.parent.children.get(currentnodeIndex + 1));
            }
            currentnode = currentnode.parent;
            currentnodeIndex = currentnode.parent == null ? -1 : currentnode.parent.children.indexOf(currentnode);
        }

        return null; // No more leaves available
    }

    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    /// ________________________________________UPDATE_DESCRIPTION_FUNCTIONALITY____________________________________________

    // Update method
    public static boolean updatePartDescription(String partId, String newDescription) {
        Part part = search(partId);
        if (part != null) {
            part.setDescription(newDescription);
            return true;
        }
        return false;
    }

    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    // *********************************************************insert_functionality*******************************************
    /**
     * inserts node into data structure
     * 
     * @param part
     */
    @SuppressWarnings("exports")
    public static void insert(Part part) {

        BPlusTreeNode leaf = findLeaf(root, part.getPartId()); // Find the appropriate leaf
        insertIntoLeaf(leaf, part); // Insert into the leaf node

        // Propagate splits up if necessary
        if (leaf.isFull()) {
            splitAndPropagate(leaf);
        }
    }

    // ****************************helper_methods
    private static void insertIntoLeaf(BPlusTreeNode leaf, Part part) {
        int i = findIndex(leaf.keys, part);

        leaf.keys.add(i, part);
    }

    private static <T> void migrateToSibling(BPlusTreeNode node, BPlusTreeNode sibling, List<T> nodeList,
            List<T> siblinList) {
        // migrate second half of list items to sibling
        int MidIndex = nodeList.size() / 2;
        siblinList.addAll(nodeList.subList(MidIndex, nodeList.size()));
        nodeList.subList(MidIndex, nodeList.size()).clear();
    }

    private static void createNewRoot(BPlusTreeNode node, BPlusTreeNode sibling, Part promotedKey) {
        root = new BPlusTreeNode(false);
        root.keys.add(promotedKey);
        root.children.add(node);
        root.children.add(sibling);
        node.parent = root;
        sibling.parent = root;
        treeHeight++;
    }

    // Propagate splits upwards
    private static void splitAndPropagate(BPlusTreeNode node) {

        splitCount++;

        BPlusTreeNode parent = node.parent;
        BPlusTreeNode sibling = new BPlusTreeNode(node.isLeaf);

        // migrate second half of keys to sibling
        migrateToSibling(node, sibling, node.keys, sibling.keys);

        Part promotedKey;
        if (!node.isLeaf) {
            // migrate second half of children to sibling
            migrateToSibling(node, sibling, node.children, sibling.children);

            // set the sibling as the parent for all its children
            for (BPlusTreeNode child : sibling.children)
                child.parent = sibling;

            promotedKey = sibling.keys.remove(0);

        } else
            promotedKey = sibling.keys.get(0); // if node is leaf do not remove the promoted key

        if (parent == null) { // If the node was the root, create a new root

            createNewRoot(node, sibling, promotedKey);

        } else {
            // if node is not root add the promoted key and the sibling to the parents keys
            // and children lists
            int i = findIndex(parent.keys, promotedKey);
            parent.keys.add(i, promotedKey);
            parent.children.add(i + 1, sibling);
            sibling.parent = parent;

            // If the parent becomes full, split it
            if (parent.isFull()) {
                splitAndPropagate(parent);
            }
        }

    }

    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///

    // **********************************************Delete_functionality***************************************************************

    /**
     * 
     * @param partId
     * @return
     */
    public static boolean delete(String partId) {

        // Find the leaf node containing the part
        BPlusTreeNode leafNode = findLeaf(root, partId);

        if (leafNode != null) {
            // Remove the part from the leaf node
            for (int i = 0; i < leafNode.keys.size(); i++) {
                if (leafNode.keys.get(i).getPartId().equals(partId)) {
                    leafNode.keys.remove(i);
                    return handleUnderflow(leafNode);
                }
            }
        }

        return false;
    }

    // --------------------Helper methods----------------\\

    public static boolean updateKeys(BPlusTreeNode node) {

        boolean wasUpdated = false;

        // if node is leaf dont update
        if (node.isLeaf) {
            return false;
        }

        // Add placeholder keys to align with corresponding children if needed
        while (node.keys.size() < node.children.size() - 1) {
            node.keys.add(null);
        }

        // Ensure keys align with children count
        for (int i = 0; i < node.keys.size() && i < node.children.size() - 1; i++) {

            // initiate corresponding key and child
            BPlusTreeNode child = node.children.get(i + 1);
            Part key = node.keys.get(i);

            // find correct key value according to corresponding child and if neccessary set
            Part minPart = findMin(child);
            if (key == null || !key.getPartId().equals(minPart.getPartId())) {
                node.keys.set(i, minPart);
                wasUpdated = true;
            }
        }

        // Trim excess keys if the node's children were reduced
        while (node.keys.size() > node.children.size() - 1) {
            node.keys.remove(node.keys.size() - 1);
            wasUpdated = true;
        }

        return wasUpdated;
    }

    @SuppressWarnings("exports")
    public static Part findMin(BPlusTreeNode node) {
        if (node.isLeaf) {
            return node.keys.get(0);
        } else
            return findMin(node.children.get(0));
    }

    private static boolean handleKeyUpdates(BPlusTreeNode node) {
        if (node != null) {
            updateKeys(node); // Always attempt to update keys in the current node
            handleKeyUpdates(node.parent); // Continue up to the root
        }
        return true;

    }

    private static boolean borrow(BPlusTreeNode node, int index, boolean isLeft) {

        // declare correct sibling
        BPlusTreeNode sibling = node.parent.children.get(isLeft ? index - 1 : index + 1);

        if (sibling.keys.size() > 2) { // make sure borrowing will not cause underflow

            // borrow the key from sibling and add it to the node

            Part borrowedKey = sibling.keys.remove(isLeft ? (sibling.keys.size() - 1) : 0);
            if (isLeft)
                node.keys.add(0, borrowedKey);
            else
                node.keys.add(borrowedKey);

            if (!node.isLeaf) { // if the node is internal also borrow a child from sibling

                // determine which child index to borrow from and which index to insert it in
                int borrowedChildIndex = isLeft ? sibling.children.size() - 1 : 0;
                int nodeAdditionIndex = isLeft ? 0 : node.children.size();

                // borrow the child, insert it, and update its parent reference.
                BPlusTreeNode borrowedChild = sibling.children.remove(borrowedChildIndex);
                node.children.add(nodeAdditionIndex, borrowedChild);
                borrowedChild.parent = node;
            }
            return handleKeyUpdates(node.parent);// update the parent's keys
        }
        return false;
    }

    private static boolean merge(BPlusTreeNode node, int index, boolean isLeft) {
        mergeCount++;

        BPlusTreeNode sibling = node.parent.children.get(isLeft ? (index - 1) : (index + 1)); // get the left sibling

        // Merge node into its sibling
        if (isLeft)
            sibling.keys.add(node.keys.remove(0));
        else
            sibling.keys.add(0, node.keys.remove(0));

        node.parent.children.remove(node); // remove the node from the parent's children list

        node.parent.keys.remove(isLeft ? (index - 1) : index); // remove unneccessary key from parent

        if (!node.isLeaf) { // if node is internal shift the children and correct their parent reference

            if (isLeft) {
                sibling.children.addAll(node.children);
            } else {
                for (int i = node.children.size() - 1; i >= 0; i--)
                    sibling.children.add(0, node.children.get(i));
            }

            for (BPlusTreeNode child : sibling.children) {
                child.parent = sibling;
            }

            handleKeyUpdates(sibling); // Update keys recursively

        }

        handleUnderflow(node.parent); // since 1 less child, handle underflow for the parent

        return true;
    }

    private static void collapseRoot(BPlusTreeNode node) {
        BPlusTreeNode newRoot = node.children.get(0);
        newRoot.parent = null;
        root = newRoot;
        handleKeyUpdates(newRoot);
    }

    private static boolean handleUnderflow(BPlusTreeNode node) {

        // first handle edge cases:
        if (node.keys.size() >= 2) { // if no Underflow
            handleKeyUpdates(node.parent);
            return true;
        } else if (node.isRoot()) { // if root

            boolean isCollapsingRoot = node.keys.isEmpty() && node.children.size() < 2;
            if (isCollapsingRoot)
                collapseRoot(node); // if root
            else
                handleKeyUpdates(node);
            return true;
        }

        BPlusTreeNode parent = node.parent;
        int index = parent.children.indexOf(node), maxIndex = parent.children.size() - 1;

        // Try borrowing from left sibling, if can't try right sibling, if cant try
        // merge with left or right sibling

        if (index > 0 && borrow(node, index, true)) {
            return true;
        } else if (index < maxIndex && borrow(node, index, false)) {
            return true;
        } else if (index > 0) {
            return merge(node, index, true);
        } else {
            return merge(node, index, false);
        }

    }

    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    ///
    // _________________________________________________Print_Methods__________________________________________________________

    /**
     * +}
     * converts entier data structure to arraylist
     * converts in flat file format
     * DO NOT GET RID OF IS USED FOR DATA STRUCTURE TO FLAT FILE
     * 
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static ArrayList printTreeDeformated() {
        ArrayList<String> returnValue = new ArrayList<String>();
        int j = 0;
        if (root == null) { // if the root is full:
            System.out.println("The tree is empty.");
        }

        // Queue for BFS traversal, each entry has a node and its child index
        Queue<Pair<BPlusTreeNode, Integer>> queue = new LinkedList<>();
        queue.add(new Pair<>(root, 0)); // Root is child 0 by default

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // Number of nodes at the current level

            while (levelSize-- > 0) {
                Pair<BPlusTreeNode, Integer> currentPair = queue.poll();
                assert currentPair != null;

                BPlusTreeNode currentNode = currentPair.getKey();

                // Print current node's keys along with child index
                for (Part key : currentNode.keys) {
                    returnValue.add(j, key.getPartId() + "        " + key.getDescription() + "\n");
                    j++;
                }

                // Add children to the queue for the next level
                if (!currentNode.isLeaf) {
                    for (int i = 0; i < currentNode.children.size(); i++) {
                        queue.add(new Pair<>(currentNode.children.get(i), i));
                    }
                }
            }
        }
        return returnValue;
    }

    public static void printTree() {
        if (root == null) { // if the root is full:
            System.out.println("The tree is empty.");
            return;
        }

        // Queue for BFS traversal, each entry has a node and its child index
        Queue<Pair<BPlusTreeNode, Integer>> queue = new LinkedList<>();
        queue.add(new Pair<>(root, 0)); // Root is child 0 by default

        System.out.println("Tree Structure:");

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // Number of nodes at the current level

            while (levelSize-- > 0) {
                Pair<BPlusTreeNode, Integer> currentPair = queue.poll();
                assert currentPair != null;

                BPlusTreeNode currentNode = currentPair.getKey();
                int childIndex = currentPair.getValue();

                // Print current node's keys along with child index
                System.out.print("Child " + childIndex + ": [ ");
                for (Part key : currentNode.keys) {
                    System.out.print(key.getPartId() + " ");
                }
                System.out.print("] ");

                // Add children to the queue for the next level
                if (!currentNode.isLeaf) {
                    for (int i = 0; i < currentNode.children.size(); i++) {
                        queue.add(new Pair<>(currentNode.children.get(i), i));
                    }
                }
            }
            // Newline to separate levels
            System.out.println("\n\n");
        }
    }

}