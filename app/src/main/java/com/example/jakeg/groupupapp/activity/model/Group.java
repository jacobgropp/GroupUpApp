package com.example.jakeg.groupupapp.activity.model;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jakeg on 11/15/2019.
 */

public class Group {

    private String groupID;
    private String name;
    private String description;
    private String nextMeetUp;
    private Drawable groupImage;

    private Map<String, User> groupMembers = new HashMap<String, User>();

    //CONSTRUCTOR
    public Group(String name){
        this.groupID = UUID.randomUUID().toString();
        this.name = name;
        this.description = "";
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

    public String getNextMeetUp() {
        return nextMeetUp;
    }

    public Drawable getGroupImage() {
        return groupImage;
    }

    public ArrayList<User> getGroupMembers() {
        return new ArrayList<>(groupMembers.values());
    }

    public User getGroupMember(String userID){
        return groupMembers.get(userID);
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

    public void setNextMeetUp(String nextMeetUp) {
        this.nextMeetUp = nextMeetUp;
    }

    public void setGroupImage(Drawable groupImage) {
        this.groupImage = groupImage;
    }

    public void addGroupMember(User groupMember) {
        this.groupMembers.put(groupMember.getUserID(), groupMember);
    }

}
