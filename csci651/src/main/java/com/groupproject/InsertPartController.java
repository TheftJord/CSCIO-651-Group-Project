package com.groupproject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

public class InsertPartController {
    

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

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void switchToSearch() throws IOException {
        App.setRoot("searchresults");
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
