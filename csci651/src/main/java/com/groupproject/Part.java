package com.groupproject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class Part implements Comparable<Part> {
    private String partId;
    private String partName;
    private String description;
    private StringProperty PartId;
    private StringProperty PartName;
    private StringProperty PartDescription;


    //-----------------------------------------------------------intializers------------------------------------------------------


    public Part(){
        this.partId = null;
        this.description = null;
        this.partName = null;
    }

    public Part(String partId, String partName, String description) {
        this.partId = partId.trim();
        this.description = description.trim();
        this.partName = partName.trim();
    }

    public Part(String partId, String partName) {
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

    public String getPartName() {
        return partName;
    }


    //--------------------------------------------------------setters----------------------------------------------------------

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPartID(String partId) {
        this.partId = partId;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }


    //-------------------------------------------------------Overrides-----------------------------------------------------------

    @Override
    public int compareTo(Part other) {
        return this.partId.compareTo(other.partId);
    }

    @Override
    public String toString() {
        return "Part ID: " + partId + ", Part Name: " + partName + ", Description: " + description;
    }


    //-------------------------------------------------------StringProperties-----------------------------------------------------


    /* public Part(String partID, String partname, String desc){
        this.PartId = new SimpleStringProperty(partID);
        this.PartName = new SimpleStringProperty(partname);
        this.PartDescription = new SimpleStringProperty(desc);
    } */
}

