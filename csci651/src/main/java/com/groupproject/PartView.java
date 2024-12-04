package com.groupproject;

/**
 * This is important for the view in the FXML
 * This is important DO NOT DELETE
 */
public class PartView {
    private String partId;
    private String description;

    //-----------------------------------------------------------intializers------------------------------------------------------


    public PartView(){
        this.partId = null;
        this.description = null;
    }

    public PartView(String partId, String description) {
        this.partId = partId.trim();
        this.description = description.trim();
    }


    //--------------------------------------------------------getters--------------------------------------------------------


    public String getPartId() {
        return this.partId;
    }

    public String getDescription() {
        return this.description;
    }


    //--------------------------------------------------------setters----------------------------------------------------------

    public void setDescription(String description) {
        this.description = description.trim();
    }

    public void setPartID(String partId) {
        this.partId = partId.trim();
    }

}
