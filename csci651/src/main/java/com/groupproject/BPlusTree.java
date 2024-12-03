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
                                            
                                                /**
                                                 * search method
                                                 * @param partId
                                                 * @return
                                                 */
                                                public static Part search(String partId) {
                                                    return searchInNode(root, new Part(partId, ""));
                                                                            }
                                                                        
                                                                            public static int getSplitCount() {
                                                                                return splitCount;
                                                                            }
                                                                        
                                                                            public static int getMargeCount() {
                                                                                return mergeCount;
                                                    }
                                                
                                                    public static int getTreeHeight() {
                                                        return treeHeight;
                                                    }
                                                
                                                    public static BPlusTreeNode getRoot() {
                                                        return root;
                                                    }
                                                
                                                    static BPlusTreeNode findLeftmostLeaf(BPlusTreeNode current) {
                                                                                                    
                                                                                                            return current;
                                                                                                        }
                                                                                                    
                                                                                                        /**
                                                                                                         * 
                                                                                                         * @param currentnode
                                                                                                         * @return
                                                                                                         */
                                                                                                        public static BPlusTreeNode findNextLeafOnTheRight(BPlusTreeNode currentnode) {
                                                                                                            int currentnodeIndex = currentnode.parent.children.indexOf(currentnode);
                                                                                                            BPlusTreeNode nextNode = currentnode.parent.children.get(currentnodeIndex + 1);
                                                                                                    
                                                                                                            if (nextNode != null) { // If the node has a direct pointer to the next leaf
                                                                                                                return nextNode;
                                                                                                            }
                                                                                                    
                                                                                                            // Otherwise, recursively move upwards to find the next leaf
                                                                                                            BPlusTreeNode parent = currentnode.parent;
                                                                                                            while (parent != null) {
                                                                                                                if (currentnodeIndex < parent.children.size() - 1) {
                                                                                                                    // Move to the sibling's subtree
                                                                                                                    return findLeftmostLeaf(parent.children.get(currentnodeIndex + 1));
                                                            }
                                                            nextNode = parent;
                                                            parent = parent.parent; // Keep moving up
                                                        }
                                                
                                                        return null; // No more leaves available
                                                    }
                                                
                                                    public static ArrayList<Part> displayNext10(Part part) {
                                                
                                                        Part searchedPart = search(part.getPartId());
                                                        if (searchedPart == null)
                                                            return null;
                                                
                                                        ArrayList<Part> returnList = new ArrayList<>();
                                                        BPlusTreeNode leaf = findLeaf(root, searchedPart.getPartId());
                                                
                                                        int index = leaf.keys.indexOf(searchedPart);
                                                
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
                                                
                                                    private static Part searchInNode(BPlusTreeNode node, Part part) {
                    
                            // find the appropriate leaf in which the part should be in
                            BPlusTreeNode leaf = findLeaf(node, part.getPartId());
                    
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
                    
                        // Update method
                        public static boolean updatePartDescription(String partId, String newDescription) {
                            Part part = search(partId);
                            if (part != null) {
                                part.setDescription(newDescription);
                                return true;
                            }
                            return false;
                        }
                    
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
                                            
                                                private static int findIndex(List<Part> list, Part part) {
                                                        int i = 0;
                                                        // find the appropriate index to insert into in the leaf
                                                        while (i < list.size() && part.compareTo(list.get(i)) > 0) {
                                                            i++;
                                                        }
                                                        return i;
                                                    }
                                                
                                                    private static void insertIntoLeaf(BPlusTreeNode leaf, Part part) {
                                                            int i = findIndex(leaf.keys, part);
                                                
                                                        leaf.keys.add(i, part);
                                                    }
                                                
                                                    private static <T> void migrateToSibling(BPlusTreeNode node, BPlusTreeNode sibling, List<T> nodeList, List<T> siblinList) {
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
                                            
                                                /**
                                                 * inserts node into data structure
                                                 * @param part
                                                 */
                                                public static void insert(Part part) {
                                            
                                                    BPlusTreeNode leaf = findLeaf(root, part.getPartId()); // Find the appropriate leaf
                        insertIntoLeaf(leaf, part); // Insert into the leaf node
            
                    // Propagate splits up if necessary
                    if (leaf.isFull()) {
                        splitAndPropagate(leaf);
        }
    }

    public static  boolean updateKeys(BPlusTreeNode node) {

        boolean wasUpdated = false;

        if (node.isLeaf) {
            // Leaf nodes do not need updates to separator keys
            return false;
        }

        while (node.keys.size() < node.children.size() - 1) {
            node.keys.add(null); // Add placeholder keys to align with new
        }
        // Ensure keys align with children count
        for (int i = 0; i < node.keys.size(); i++) {
            if (i >= node.children.size() - 1) {
                break;
            }

            BPlusTreeNode child = node.children.get(i + 1);

            // Recursively update keys in the child
            wasUpdated = updateKeys(child);

            // Get the minimum key from the child
            Part minPart = findMin(child);

            // Update the separator key if necessary
            if (node.keys.get(i) == null || !node.keys.get(i).getPartId().equals(minPart.getPartId())) {
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

    public static Part findMin(BPlusTreeNode node) {
        if (node.isLeaf) {
            return node.keys.get(0);
        } else
            return findMin(node.children.get(0));
    }

    // Delete method
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
                    
                        private static boolean handleKeyUpdates(BPlusTreeNode node) {
                                if (node != null) {
                                    updateKeys(node); // Always attempt to update keys in the current node
                                    handleKeyUpdates(node.parent); // Continue up to the root
                                }
                                return true;
                        
                            }
                        
                            public static void printNode(BPlusTreeNode node) {
                                if (node != null) {
                                    for (Part key : node.keys) {
                                        if (key != null) {
                                            System.out.print(key.getPartId() + " ");
                                        }
                                    }
                                }
                            }
                        
                            public static boolean isRoot(BPlusTreeNode node) {
                                return root == node;
                            }
                        
                            public static boolean borrow(BPlusTreeNode node, int index, boolean isLeft) {
                        
                                BPlusTreeNode sibling = node.parent.children.get(isLeft ? index - 1 : index + 1);
                        
                                if (sibling.keys.size() > 2) {
                        
                                    Part borrowedKey = sibling.keys.remove(isLeft ? (sibling.keys.size() - 1) : 0);
                        
                                    if (isLeft)
                                        node.keys.add(0, borrowedKey);
                                    else
                                        node.keys.add(borrowedKey);
                        
                                    if (!node.isLeaf) { // if the node is internal also borrow a child from sibling
                                        int borrowedChildIndex = isLeft ? sibling.children.size() - 1 : 0;
                                        int nodeAdditionIndex = isLeft ? 0 : node.children.size();
                        
                                        BPlusTreeNode borrowedChild = sibling.children.remove(borrowedChildIndex);
                                        node.children.add(nodeAdditionIndex, borrowedChild);
                                        borrowedChild.parent = node;
                                    }
                                    return handleKeyUpdates(node.parent);
                                }
                                return false;
                            }
                        
                            public static boolean merge(BPlusTreeNode node, int index, boolean isLeft) {
                        
                                BPlusTreeNode sibling = node.parent.children.get(isLeft ? (index - 1) : (index + 1)); // get the left sibling
                        
                                if (isLeft)
                                    sibling.keys.add(node.keys.remove(0));
                                else
                                    sibling.keys.add(0, node.keys.remove(0));
                        
                                node.parent.children.remove(node); // remove the node from the parent's children list
                        
                                node.parent.keys.remove(isLeft ? (index - 1) : index); // less children means less keys so remove the key
                                                                                       // associated with the node
                        
                                if (!node.isLeaf) {
                        
                                    // if left sibling append children on the right but if right sibling add
                                    // children from the left
                                    if (isLeft)
                                        sibling.children.addAll(node.children);
                                    else
                                        for (int i = node.children.size() - 1; i >= 0; i--)
                                            sibling.children.add(0, node.children.get(i));
                        
                                    for (BPlusTreeNode child : sibling.children) {
                                        child.parent = sibling;
                                    }
                        
                                    handleKeyUpdates(sibling); // Update keys recursively
                    
                            }
                    
                            handleUnderflow(node.parent); // recursive call for the parent
                    
                            return true;
                        }
                    
                        private static boolean handleUnderflow(BPlusTreeNode node) {

        if (node.keys.size() >= 2) { // if there was no underflow make sure parents keys are correct
            handleKeyUpdates(node.parent);
            return true;
        } else if (isRoot(node)) { //
            // If the root has no keys and only one child, collapse the tree
            if (node.keys.isEmpty() && node.children.size() == 1) {
                BPlusTreeNode newRoot = node.children.get(0);
                newRoot.parent = null;
                root = newRoot;
                updateKeys(newRoot);
            } else {
                // Update keys in the root if it remains a valid root
                updateKeys(node);
            }
            return true;
        }

        BPlusTreeNode parent = node.parent;
        int index = parent.children.indexOf(node);

        // Try borrowing from left sibling
        if (index > 0) {

            if (borrow(node, index, true))
                return true;
        }

        if (index < parent.children.size() - 1) { // Try borrowing from right sibling
            if (borrow(node, index, false))
                return true;
        }

        if (index > 0) {
            return merge(node, index, true); // If borrowing isn't possible, merge with a left sibling
        } else {
            return merge(node, index, false); // if cant borrow or merge with left sibling, merge with right sibling
        }

    }
    /*
     * private BPlusTreeNode root;
     * private int treeHeight;
     * 
     * // Tracking
     * private int splitCount = 0;
     * private int mergeCount = 0;
     * 
     * public static static BPlusTree() {
     * root = new BPlusTreeNode(true);
     * treeHeight = 1;
     * }
     * 
     * // Search method
     * public static static Part search(String partId) {
     * return searchInNode(root, new Part(partId, ""));
     * }
     * 
     * public static static int getSplitCount() {
     * return splitCount;
     * }
     * 
     * public static static int getMargeCount() {
     * return mergeCount;
     * }
     * 
     * public static static int getTreeHeight() {
     * return treeHeight;
     * }
     * 
     * private Part searchInNode(BPlusTreeNode node, Part part) {
     * if (node.isLeaf) {
     * // Search in leaf node
     * for (Part p : node.keys) {
     * if (p.getPartId().equals(part.getPartId())) {
     * return p;
     * }
     * }
     * return null;
     * }
     * 
     * // Navigate internal nodes
     * for (int i = 0; i < node.keys.size(); i++) {
     * if (part.compareTo(node.keys.get(i)) < 0) {
     * return searchInNode(node.children.get(i), part);
     * }
     * }
     * return searchInNode(node.children.get(node.children.size() - 1), part);
     * }
     * 
     * // Update method
     * public static static boolean updatePartDescription(String partId, String newDescription) {
     * Part part = search(partId);
     * if (part != null) {
     * part.setDescription(newDescription);
     * return true;
     * }
     * return false;
     * }
     * 
     * // Insert method
     * public static static void insert(Part part) {
     * // If root is full, split it
     * if (root.isFull()) {
     * BPlusTreeNode newRoot = new BPlusTreeNode(false);
     * newRoot.children.add(root);
     * splitChild(newRoot, 0);
     * root = newRoot;
     * treeHeight++;
     * splitCount++;
     * }
     * insertNonFull(root, part);
     * }
     * 
     * private void insertNonFull(BPlusTreeNode node, Part part) {
     * if (node.isLeaf) {
     * // Insert into leaf node
     * insertIntoLeaf(node, part);
     * } else {
     * // Find appropriate child to insert into
     * int index = 0;
     * while (index < node.keys.size() && part.compareTo(node.keys.get(index)) > 0)
     * {
     * index++;
     * }
     * 
     * BPlusTreeNode child = node.children.get(index);
     * 
     * // Split child if full
     * if (child.isFull()) {
     * splitChild(node, index);
     * // Adjust index if needed after split
     * if (part.compareTo(node.keys.get(index)) > 0) {
     * index++;
     * }
     * }
     * insertNonFull(node.children.get(index), part);
     * }
     * }
     * 
     * private void insertIntoLeaf(BPlusTreeNode leaf, Part part) {
     * int insertIndex = 0;
     * while (insertIndex < leaf.keys.size() &&
     * leaf.keys.get(insertIndex).compareTo(part) < 0) {
     * insertIndex++;
     * }
     * leaf.keys.add(insertIndex, part);
     * }
     * 
     * // Split child method
     * private void splitChild(BPlusTreeNode parent, int childIndex) {
     * BPlusTreeNode child = parent.children.get(childIndex);
     * BPlusTreeNode newChild = new BPlusTreeNode(child.isLeaf);
     * 
     * // Move half the keys to the new node
     * int midPoint = child.keys.size() / 2;
     * newChild.keys.addAll(child.keys.subList(midPoint, child.keys.size()));
     * child.keys.subList(midPoint, child.keys.size()).clear();
     * 
     * // If not a leaf, move children as well
     * if (!child.isLeaf) {
     * newChild.children.addAll(child.children.subList(midPoint,
     * child.children.size()));
     * child.children.subList(midPoint, child.children.size()).clear();
     * }
     * 
     * // Insert middle key into parent
     * parent.keys.add(childIndex, newChild.keys.get(0));
     * parent.children.add(childIndex + 1, newChild);
     * }
     * 
     * // Delete method
     * public static static boolean delete(String partId) {
     * // Find the leaf node containing the part
     * BPlusTreeNode leafNode = findLeafNode(root, partId);
     * 
     * if (leafNode != null) {
     * // Remove the part from the leaf node
     * for (int i = 0; i < leafNode.keys.size(); i++) {
     * if (leafNode.keys.get(i).getPartId().equals(partId)) {
     * leafNode.keys.remove(i);
     * return true;
     * }
     * }
     * }
     * 
     * return false;
     * }
     * 
     * private BPlusTreeNode findLeafNode(BPlusTreeNode node, String partId) {
     * // If it's a leaf node, return it
     * if (node.isLeaf) {
     * return node;
     * }
     * 
     * // Navigate through internal nodes
     * for (int i = 0; i < node.keys.size(); i++) {
     * if (partId.compareTo(node.keys.get(i).getPartId()) < 0) {
     * return findLeafNode(node.children.get(i), partId);
     * }
     * }
     * 
     * // If we've gone through all keys, go to the last child
     * return findLeafNode(node.children.get(node.children.size() - 1), partId);
     * }
     * public static static void displayNextTenParts(String startPartId) {
     * BPlusTreeNode currentNode = findLeafNode(root, startPartId);
     * if (currentNode == null) {
     * System.out.println("Part ID not found.");
     * return;
     * }
     * 
     * int startIndex = 0;
     * for (int i = 0; i < currentNode.keys.size(); i++) {
     * if (currentNode.keys.get(i).getPartId().equals(startPartId)) {
     * startIndex = i + 1;
     * break;
     * }
     * }
     * 
     * int count = 0;
     * while (currentNode != null && count < 10) {
     * for (int i = startIndex; i < currentNode.keys.size() && count < 10; i++) {
     * System.out.println(currentNode.keys.get(i));
     * count++;
     * }
     * currentNode = currentNode.nextLeaf;
     * startIndex = 0;
     * }
     * }
     */
}
