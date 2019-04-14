package com.ttn.LinkSharing.entities;

public enum ResourceType {

    LINK("Link Resource"),
    DOCUMENT("Document Resource");
    String value;
    ResourceType(String resourceType){
        value = resourceType;
    }
    public String getValue(){
        return value;
    }

}