package com.groupproject;

/**
 * This will be the functions of the interface that will interact with the B+-Tree
 */

public class InterfaceFunctions{

    /**
     * This will take the part name from testFieldText;
     * it will prompt the user to enter more part information on a new scene
     * this will properly insert the node into the machine
     */
    public static void insertFunction(){
        String partName = PrimaryController.textFieldText(); //this will get the part name so you can search it up
        /*Switch scenes*/
        /*take partName, request more information from user on new scene */
        /*use b+-tree insert function to add information to the tree */
    }

    /**
     * This will take the part name from testFieldText
     * it will present the user with 10 similar items to the desired result
     * this will switch scenes
     */
    public static void searchFunction(){
        String partName = PrimaryController.textFieldText(); //this will get the part name so you can search it up
        /*Switch scenes*/
        /*search the B+-Tree for the top ten results */
        /*display next ten results on the new scene */
    }

    /**
     * This will remove the searched object from the tree
     * still deciding whether switch scenes or not
     */
    public static void removeFunction(){
        String partName = PrimaryController.textFieldText(); //this will get the part name so you can search it up
        /*search tree for part */
        /*remove part from Tree */
        /*confirm if part has been removed, if not send alert box */
    }

    /**
     * will open up file explorer for the user to use
     */
    public void fileOpening(){

    }
    /**
     * will save file to designed spot for root to be spotless
     * should be calling fileOpening to open up file explorer to decide where to save the file to
     */

    public void savingFiles(){

    }
}