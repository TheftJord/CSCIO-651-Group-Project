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
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)","*.txt");

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
     * will start process of shifting data from data structuer to file
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
        String temp= itemSearchBar.getText();   //saves text to temporary string value to be able to return the search value when needed
        return temp;    //returns strinng value to allow other methods to use the value
    }

    /**
     * Gets searched item
     * searches for item
     * swaps to other scene upon finding item
     * @throws IOException
     */
    @FXML
    private void switchToSearch() throws IOException {
        String searchedItem=retrieveSearchItem();   //gets searched item from searchbar to be able to use in the code
        /* searchMethod(searchedItem); */   //sends searched item to search method found in B+-Tree
        
        /* insert check method to make sure that item is found */
        /* send out pop up if item is not found in database */

        App.setRoot("searchresults");   //swaps to searchresults fxml scene
    }

    /**
     * swaps to insertparts fxml immediately
     * needed way to swap to other page without going to search page first
     * no need to insert anything in this method
     * @throws IOException
     */
    @FXML
    private void switchtoInsertPart() throws IOException{
        App.setRoot("insertpart");  //swaps to insertparts fxml scene 
    }

    /**
     * closes application safely to a make sure that everything is closed safely
     */
    @FXML
    private void closeApplication(){
        saveFile();
        Platform.exit();    //closes platform 
        System.exit(0);    //closes system
    }
}
