package com.groupproject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;

public class FileManager {

    static FileChooser fileChooser = new FileChooser();
    static File current=null;
    static File selectedFile;
    FileReader fr;
    
    /**
     * this will open file explorer
     * allows user to pick file
     * will start process of shifting data from file to data structure
     */
    @FXML
    public static void openFile(){
        selectedFile=fileChooser.showOpenDialog(null);  //opens file explorer
        try {
            current = new File(new File(".").getCanonicalPath()); //gets path for file
        } catch (IOException e) {
            e.printStackTrace(); //error report to know why file retrieval failed
        }
        fileChooser.setInitialDirectory(current);
        PartCatalog.loadFromFile(selectedFile.getName());
    }

    /**
     * this will open file explorer
     * allows user to pick existing file or make new file
     * will start process of shifting data from data structuer to file
     */
    @FXML
    public static void saveFile(){
        selectedFile=fileChooser.showSaveDialog(null);  //opens file explorer
        /* will start function of converting data structure to saved file indicated by selectedFile */
    }
}
