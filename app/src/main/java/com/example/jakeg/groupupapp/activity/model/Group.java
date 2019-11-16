package com.example.jakeg.groupupapp.activity.model;

import android.media.Image;

import java.util.List;
import java.util.UUID;

/**
 * Created by jakeg on 11/15/2019.
 */

public class Group {

    private String groupID;
    private String name;
    private String description;
    private Image groupImage;

    private List<User> groupMembers;

    //CONSTRUCTOR
    public Group(String name){
        this.groupID = UUID.randomUUID().toString();
        this.name = name;
    }

    //GETTERS

    public String getGroupID() {
        return groupID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Image getGroupImage() {
        return groupImage;
    }

    public List<User> getGroupMembers() {
        return groupMembers;
    }

    //SETTERS

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGroupImage(Image groupImage) {
        this.groupImage = groupImage;
    }

    public void setGroupMembers(List<User> groupMembers) {
        this.groupMembers = groupMembers;
    }
}
