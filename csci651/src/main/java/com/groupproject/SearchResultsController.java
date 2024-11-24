package com.groupproject;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableRow;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class SearchResultsController {

    @FXML
    public TableView PartList;

    FileChooser fileChooser = new FileChooser();
    File current=null,selectedFile;
    FileReader fr;
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)","*.txt");

    /**
     * initializer method
     * will run any code to allow this program to run properly and smoothly
     */
    public void initialize(){
        /*
         * File Chooser Set Up
         * DO NOT TOUCH ALREADY DONE
         */
        fileChooser.getExtensionFilters().add(extFilter);
        try{
            current=new File(new File(".").getCanonicalPath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        fileChooser.setInitialDirectory(current);

        /*
         * pulls information from data structure
         * puts information into TableView
         */
    }

    @FXML
    private void openFile(){
        selectedFile=fileChooser.showOpenDialog(null);  //opens file explorer
        if(selectedFile!=null){    //if file selected will call function to add information to data structure
            /* function to add data to the data structure */
        }
    }

    @FXML
    private void saveFile(){
        selectedFile=fileChooser.showSaveDialog(null);  //opens file explorer
        /* will start function of converting data structure to saved file indicated by selectedFile */
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void switchtoInsertPart() throws IOException{
        App.setRoot("insertpart");
    }

    /**
     * removes method from data structure when needed
     * makes it possible to remove parts from the list
     */
    @FXML
    private void removePart(){
        /* insert method to remove part from data structure */
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