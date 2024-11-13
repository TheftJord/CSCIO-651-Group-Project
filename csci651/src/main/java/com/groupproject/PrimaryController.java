package com.groupproject;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PrimaryController {

    static TextField BaseIntefaceTextBox = new TextField(); // textfield object in interface

    /**
     * This will get the text from the TextField and then will return it so it can be used when needed
     * @return
     */
    public static String textFieldText (){
        return BaseIntefaceTextBox.getText();
    }

    /**
     * When the insert button is pressed it will call upon this function and will proceed with whatever is within this function
     */
    public void insertButtonAction(){
        /*interfaceFunctions.insertFunction();*/
    }

    /**
     * When the search button is pressed it will call upon this function and will proceed with whatever is within this function
     */
    public void searchButtonAction(){
        /* interfaceFunctions.searchFunction(); */
    }

    /**
     * When the remove button is pressed it will call upon this function and will proceed with whatever is within this function
     */
    public void removeButtonAction(){
        /* interfaceFunctions.removeFunction(); */
    }

    /**
     * This will open up file expolorer
     * The user will be able to pick a flat filet to open and fill the B+-tree with
     */
    public void selectFileAction(){

    }

    /**
     * This will allow the user to save the tree to a flat file for later
     * This will open file explorer
     * this will allow the user to select where the file is being save and if wanted which file is being replaced
     */
    public void saveFileAction(){

    }

    /**
     * This will close the application
     * This will prompt the user to save the tree to a flat file
     * this will open file explorer
     * (most likely will call upon saveFileAction's code to reduce redunecies)
     */
    public void exitAction(){
        
    }
}
