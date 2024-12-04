package com.groupproject;

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


    //-------------------------------------------------------Overrides-----------------------------------------------------------

    @Override
    public int compareTo(Part other) {
        return this.partId.compareTo(other.partId);
    }

    @Override
    public String toString() {
        return "Part ID: " + partId + ", Description: " + description;
    }
}

