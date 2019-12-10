package com.example.jakeg.groupupapp.activity.model;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {
    private String userID;
    private String username;
    private String emailAddress;
    private String phoneNumber;

    private Drawable userPicture;

    private Map<String, Group> groups = new HashMap<String, Group>();

    private GroupMember userGroupMember;

    //CONSTRUCTOR
    public User(String username, String emailAddress, String phoneNumber) {
        this.userID = UUID.randomUUID().toString();
        this.username = username;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.userGroupMember = new GroupMember(username, emailAddress, phoneNumber);
    }

    //GROUP FUNCTIONS
    public void addGroup(Group group){
        groups.put(group.getGroupID(), group);
    }

    public void removeGroup(String groupID){
        groups.remove(groupID);
    }

    public Group getGroup(String groupID){
        return groups.get(groupID);
    }

    public ArrayList<Group> getGroups(){
        return new ArrayList(groups.values());
    }

    //GETTERS

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Drawable getUserPicture() {
        return userPicture;
    }

    public GroupMember getUserGroupMember() {
        return userGroupMember;
    }

    //SETTERS

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUserPicture(Drawable userPicture) {
        this.userPicture = userPicture;
    }

    public void setGroups(Map<String, Group> groups) {
        this.groups = groups;
    }

    public void setUserGroupMember(GroupMember userGroupMember) {
        this.userGroupMember = userGroupMember;
    }


}
