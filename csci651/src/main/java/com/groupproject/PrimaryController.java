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

    //----------------------------------------------------------------Variables----------------------------------------------------------------------


    //FXML Variables
    @SuppressWarnings("exports")
    public TextField itemSearchBar;
    @SuppressWarnings("exports")
    public TextField EnterPartNum;
    @SuppressWarnings("exports")
    public TextField EnterPartDesc;
    @SuppressWarnings("exports")
    public TextField UpdatePartDesc;
    @SuppressWarnings("exports")
    public TextField UpdatePartID;
    @SuppressWarnings("exports")
    public TextField RemovePart;
    @SuppressWarnings({"rawtypes" })
    @FXML
    private TableView ViewTable = new TableView<>();
    @FXML
    private TableColumn<PartView,String> KeyCol = new TableColumn<>();
    @FXML
    private TableColumn<PartView,String> DescCol = new TableColumn<>();
    @SuppressWarnings("exports")
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
     * @SuprressWarnings
     */
    @SuppressWarnings("unchecked")
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
        KeyCol.setCellValueFactory(new PropertyValueFactory<PartView,String>("partId"));//sets up Part Id Column
        DescCol.setCellValueFactory(new PropertyValueFactory<PartView,String>("description"));; //sets up Part Description Column
    }

    //--------------------------------------------------------------Menu---------------------------------------------------------------------


    /**
     * this will open file explorer
     * allows user to pick file
     * will start process of shifting data from file to data structure
     */
    @FXML
    private void menuOpenFile(){
        //File Explorer Code
        selectedFile=fileChooser.showOpenDialog(null);  //opens file explorer
        try {
            current = new File(new File(".").getCanonicalPath()); //gets path for file
        } catch (IOException e) {
            e.printStackTrace(); //error report to know why file retrieval failed
        }
        fileChooser.setInitialDirectory(current); //sets the inital directory
        PartCatalog.loadFromFile(selectedFile.getName()); //transfers from file to data structure

        //updates required information
        textUpdate(); //updates information at the bottom of UI
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
        String partDesc = EnterPartDesc.getText(); //gets Part Description

        //converts strings to part
        Part temp = new Part(partID,partDesc); //creates temporary part inorder to add to data structure

        //inserts part into data structure
        BPlusTree.insert(temp); //inserts temporary part into data structure

        //update text at bottom
        textUpdate();

        //Clears textfields When Done
        EnterPartNum.clear(); //Clears Part ID textfield
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

        //deletes part from data structure
        BPlusTree.delete(partID); //removes part from data structure by part ID

        //update text at bottom
        textUpdate();

        //clears textfield when done
        RemovePart.clear(); //clears Remove Part textfield
    }

    /**
     * updates description for part in data structure
     * gets id from UpdatePartID
     * gets updated description from UpdatePartDesc
     * @throws IOException
     */
    @FXML
    private void tabPaneUpdate() throws IOException{
        //gets entered text from textfield inorder to use in later code
        String partID = UpdatePartID.getText(); //gets part Id inorder to find part to update
        String partDesc = UpdatePartDesc.getText(); //gets part desc. to update previous part desc.

        //updates information on data structure
        BPlusTree.updatePartDescription(partID,partDesc);

        //clears textfields when done
        UpdatePartID.clear(); //clears UpdatePartID for next use
        UpdatePartDesc.clear(); //clears UpdatePartDesc for next use
    }

    /**
     * cleans up textfields inbetween switching tabs
     * quality of life change
     * @throws IOException
     */
    @FXML
    private void tabPaneClear() throws IOException{
        //clears search tab's textfields
        itemSearchBar.clear(); //clears search textfield

        //clears insert tab's textfields
        EnterPartNum.clear(); //Clears Part ID textfield
        EnterPartDesc.clear(); //Clears Part Description textfield

        //clears remove tab's textfield
        RemovePart.clear(); //clears Remove Part textfield

        //clears update tab's textfields
        UpdatePartID.clear(); //clears UpdatePartID for next use
        UpdatePartDesc.clear(); //clears UpdatePartDesc for next use
    }
    
    
    //--------------------------------------------------------------Table View--------------------------------------------------------------------

    /**
     * adds items to TableView to display tree
     * will search data structure for items and add them to view
     */
    @SuppressWarnings("unchecked")
    @FXML
    private void tableViewAddItems(){
        ObservableList<PartView> insertList = ViewTable.getItems(); //makes observable list to use for TableView
        insertList.clear(); //clears observable list to prevent old items from entering the list
        Part holder = BPlusTree.search(searchValue); //converts search value to equalivant part

        for(Part temp:BPlusTree.displayNext10(holder)){ //will take items from data structure
            PartView Swapper = new PartView(temp.getPartId(),temp.getDescription());
            insertList.add(Swapper); //adds desired items to observerable list
            //ViewTable.setItems(insertList);
        }
    }


    //-------------------------------------------------------------update text--------------------------------------------------------------------


    /**
     * updates text to show required information
     * will be used at the end of TabPane methods
     */
    @FXML
    private void textUpdate(){
        //makes temporary variable
        String updatedText = null;

        //gets information from tree
        updatedText = BPlusTree.infoToString(); //sets the temp. var. to the required information

        //updates text on UI
        TreeInfo.setText(updatedText);
    }
}

/**
 * By Theft_Jord
 */
