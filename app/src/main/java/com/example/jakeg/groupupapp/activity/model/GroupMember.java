package com.example.jakeg.groupupapp.activity.model;

import android.graphics.drawable.Drawable;
import android.media.Image;

import java.util.Map;
import java.util.UUID;

/**
 * Created by jakeg on 11/15/2019.
 */

public class GroupMember {

    private String groupMemberID;
    private String name;
    private String emailAddress;
    private String phoneNumber;

    private Drawable userPicture;

    private String attending;

    //CONSTRUCTOR
    public GroupMember(String name, String emailAddress, String phoneNumber) {
        this.groupMemberID = UUID.randomUUID().toString();
        this.name = name;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        setAttending(false);
    }

    //GETTERS

    public String getName() {
        return name;
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

    public String getAttending() {
        return attending;
    }

    public String getGroupMemberID() {
        return groupMemberID;
    }

    //SETTERS

    public void setUsername(String name) {
        this.name = name;
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

    public void setAttending(Boolean attending) {
        if(attending){
            this.attending = "Yes";
        }
        else{
            this.attending = "No";
        }
    }

}

