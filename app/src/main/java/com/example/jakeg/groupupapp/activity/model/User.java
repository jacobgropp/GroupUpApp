package com.example.jakeg.groupupapp.activity.model;

import android.graphics.drawable.Drawable;
import android.media.Image;

import java.util.UUID;

/**
 * Created by jakeg on 11/15/2019.
 */

public class User {

    private String userID;
    private String username;
    private String emailAddress;
    private String phoneNumber;

    private Drawable userPicture;

    //CONSTRUCTOR
    public User(String username, String emailAddress, String phoneNumber) {
        this.userID = UUID.randomUUID().toString();
        this.username = username;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
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
}

