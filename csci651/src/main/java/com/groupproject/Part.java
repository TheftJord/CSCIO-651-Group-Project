package com.groupproject;

class Part implements Comparable<Part> {
    private String partId;
    private String description;

    public Part(String partId, String description) {
        this.partId = partId.trim();
        this.description = description.trim();
    }

    public String getPartId() {
        return partId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(Part other) {
        return this.partId.compareTo(other.partId);
    }

    @Override
    public String toString() {
        return "Part ID: " + partId + ", Description: " + description;
    }
}

