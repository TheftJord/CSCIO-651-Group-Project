package com.groupproject;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.StringProperty;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javafx.collections.ObservableList;

public class PrimaryController {

    //----------------------------------------------------------------variables----------------------------------------------------------------------


    //FXML Variables
    public TextField itemSearchBar;
    public TextField EnterPartNum;
    public TextField EnterPartName;
    public TextField EnterPartDesc;
    public TextField RemovePart;
    public TableView ViewTable;
    public TableColumn KeyCol;
    public TableColumn NameCol;
    public TableColumn DescCol;
    public Text TreeInfo;

    //File Interface Variables
    FileChooser fileChooser = new FileChooser();
    File current=null,selectedFile;
    FileReader fr;
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)","*.txt");

    //Code Variables
    BPlusTree bTree = new BPlusTree();
    Part part = new Part();
    ArrayList<Part> parts;
    String searchValue = null;


    //---------------------------------------------------------------Background Code-----------------------------------------------------------------


    /**
     * initializer method
     * will run any code to allow this program to run properly and smoothly
     */
    public void initialize(){
        //sets up file explorer
        fileChooser.getExtensionFilters().add(extFilter);
        try{
            current=new File(new File(".").getCanonicalPath()); //gets path for file
        } catch (IOException ex) {
            ex.printStackTrace(); //error report to know why file retrieval failed
        }
        fileChooser.setInitialDirectory(current); //sets the inital directory

        //allows user to choose intial file
        menuOpenFile(); //opens up file explorer to allow user to choose initial file

        //sets up TableView Columns for use
        KeyCol.setCellValueFactory(new PropertyValueFactory<>("partId")); //sets up Part Id Column
        NameCol.setCellFactory(new PropertyValueFactory<>("partName")); //sets up Part Name Column
        DescCol.setCellFactory(new PropertyValueFactory<>("description")); //sets up Part Description Column
    }

    //--------------------------------------------------------------Menu---------------------------------------------------------------------


    /**
     * this will open file explorer
     * allows user to pick file
     * will start process of shifting data from file to data structure
     */
    @FXML
    private void menuOpenFile(){
        selectedFile=fileChooser.showOpenDialog(null);  //opens file explorer
        try {
            current = new File(new File(".").getCanonicalPath()); //gets path for file
        } catch (IOException e) {
            e.printStackTrace(); //error report to know why file retrieval failed
        }
        fileChooser.setInitialDirectory(current); //sets the inital directory
        PartCatalog.loadFromFile(selectedFile.getName()); //transfers from file to data structure
    }

    /**
     * this will open file explorer
     * allows user to pick existing file or make new file
     * will start process of shifting data from data structuer to file
     */
    @FXML
    private void menuSaveFile(){
        selectedFile=fileChooser.showSaveDialog(null);  //opens file explorer
        /* will start function of converting data structure to saved file indicated by selectedFile */
    }

    /**
     * closes application safely to a make sure that everything is closed safely
     */
    @FXML
    private void menuCloseApplication(){
        //saves file before closing application
        menuSaveFile(); //opens file explorers and prompts user to save to file

        //closes application safetly
        Platform.exit();    //closes platform 
        System.exit(0);    //closes system
    }


    //---------------------------------------------------------------Tab Pane------------------------------------------------------------------------


    /**
     * Gets searched item
     * searches for item
     * @throws IOException
     */
    @FXML
    private void tabPaneSearch() throws IOException {
        //Gets entered text from textfield inorder to use in later code
        String searchedItem = itemSearchBar.getText(); //Gets searched item

        //sets the searched item to a universal value to use in other methods
        searchValue = searchedItem; //sets searchValue to the searched item

        //adds values to TableView
        tableViewAddItems(); //adds item to TableView and will automatically pull them from the tree
        
        /* insert check method to make sure that item is found */
        /* send out pop up if item is not found in database */

        //update text at bottom
        textUpdate();

        //clears search bar
        itemSearchBar.clear(); //clears search textfield
    }

    /**
     * gets values to add to data structure
     * adds values to make item in data structure
     * @throws IOException
     */
    @FXML
    private void tabPaneInsert() throws IOException{
        //Gets entered text from textfields inorder to use later in code
        String partID = EnterPartNum.getText(); //gets Part ID
        String partName = EnterPartName.getText(); //gets Part Name
        String partDesc = EnterPartDesc.getText(); //gets Part Description


        //update text at bottom
        textUpdate();

        //Clears textfields When Done
        EnterPartNum.clear(); //Clears Part ID textfield
        EnterPartName.clear(); //Clears Part Name textfield
        EnterPartDesc.clear(); //Clears Part Description textfield
    }
    
    /**
     * gets part id from textfield
     * removes part from data structure
     * @throws IOException
     */
    @FXML
    private void tabPaneRemove() throws IOException{
        //gets entered text from textfield inorder to use in later code
        String partID = RemovePart.getText(); //gets Part ID

        //update text at bottom
        textUpdate();

        //clears textfield when done
        RemovePart.clear(); //clears Remove Part textfield
    }
    
    
    //--------------------------------------------------------------Table View--------------------------------------------------------------------

    /**
     * adds items to TableView to display tree
     * will search data structure for items and add them to view
     */
    @FXML
    private void tableViewAddItems(){
        ObservableList<Part> insertList = ViewTable.getItems(); //makes observable list to use for TableView
        insertList.clear(); //clears observable list to prevent old items from entering the list
        for(Part temp:updatedBPlusTree.displayNextParts(searchValue)){ //will take items from data structure
            insertList.add(temp); //adds desired items to observerable list
        }
    }


    //-------------------------------------------------------------update text--------------------------------------------------------------------


    /**
     * updates text to show required information
     * will be used at the end of TabPane methods
     */
    @FXML
    private void textUpdate(){
        /* insert code to update text to show requested information */
    }
}
