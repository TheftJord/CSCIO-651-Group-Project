package com.groupproject;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML
    public TextField itemSearchBar;
    
    @FXML
    private String retrieveSearchItem(){
        String temp= itemSearchBar.getText();
        return temp;
    }

    @FXML
    private void testingFeatures(){
        System.out.println(retrieveSearchItem());
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("searchresults");
    }
}
