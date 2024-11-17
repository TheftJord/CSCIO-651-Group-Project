package com.groupproject;

import java.io.IOException;
import javafx.fxml.FXML;

public class SearchResultsController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}