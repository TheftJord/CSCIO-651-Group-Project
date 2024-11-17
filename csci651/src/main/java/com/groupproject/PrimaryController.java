package com.groupproject;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class PrimaryController {

    @FXML
    public TextField itemSearchBar;

    FileChooser fileChooser = new FileChooser();
    File current=null,selectedFile;
    FileReader fr;
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)","*.json");

    /**
     * initializer method
     * will run any code to allow this program to run properly and smoothly
     */
    public void initialize(){
        fileChooser.getExtensionFilters().add(extFilter);
        try{
            current=new File(new File(".").getCanonicalPath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        fileChooser.setInitialDirectory(current);
    }

    /**
     * this will open file explorer
     * allows user to pick file
     * will start process of shifting data from file to data structure
     */
    @FXML
    private void openFile(){
        selectedFile=fileChooser.showOpenDialog(null);  //opens file explorer
        if(selectedFile!=null){    //if file selected will call function to add information to data structure
            /* function to add data to the data structure */
        }
    }

    /**
     * this will open file explorer
     * allows user to pick existing file or make new file
     * will start process of shifting data fro data structuer to file
     */
    @FXML
    private void saveFile(){
        selectedFile=fileChooser.showSaveDialog(null);  //opens file explorer
        /* will start function of converting data structure to saved file indicated by selectedFile */
    }
    
    /**
     * this will get text from search bar to be used in other methods
     * @return
     */
    @FXML
    private String retrieveSearchItem(){
        String temp= itemSearchBar.getText();   //saves text to temporary string value
        return temp;    //returns striung value
    }

    /**
     * Gets searched item
     * searches for item
     * swaps to other scene upon finding item
     * @throws IOException
     */
    @FXML
    private void switchToSearch() throws IOException {
        String searchedItem=retrieveSearchItem();   //gets searched item from searchbar
        /* searchMethod(searchedItem); */   //sends searched item to search method found in B+-Tree
        
        /* insert check method to make sure that item is found */
        /* send out pop up if item is not found in database */

        App.setRoot("searchresults");   //swaps to searchresults fxml scene
    }

    /**
     * closes application safely
     */
    @FXML
    private void closeApplication(){
        Platform.exit();    //closes platform
        System.exit(0);    //closes system
    }
}
