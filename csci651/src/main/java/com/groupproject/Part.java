package com.groupproject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class Part implements Comparable<Part> {
    private String partId;
    private String description;

    //-----------------------------------------------------------intializers------------------------------------------------------


    public Part(){
        this.partId = null;
        this.description = null;
    }

    public Part(String partId, String description) {
        this.partId = partId.trim();
        this.description = description.trim();
    }


    //--------------------------------------------------------getters--------------------------------------------------------


    public String getPartId() {
        return partId;
    }

    public String getDescription() {
        return description;
    }


    //--------------------------------------------------------setters----------------------------------------------------------

    public void setDescription(String description) {
        this.description = description.trim();
    }

    public void setPartID(String partId) {
        this.partId = partId.trim();
    }


    //-------------------------------------------------------Overrides-----------------------------------------------------------

    @Override
    public int compareTo(Part other) {
        return this.partId.compareTo(other.partId);
    }

    @Override
    public String toString() {
        return "Part ID: " + partId + ", Description: " + description;
    }


    //-------------------------------------------------------StringProperties-----------------------------------------------------


    /* public Part(String partID, String partname, String desc){
        this.PartId = new SimpleStringProperty(partID);
        this.PartName = new SimpleStringProperty(partname);
        this.PartDescription = new SimpleStringProperty(desc);
    } */
}

