package com.example.jakeg.groupupapp.activity.model;

import java.util.Observable;

/**
 * Created by jakeg on 11/15/2019.
 */

public class Model extends Observable {

    public static Model model;

    public static Model getModel() {
        if (model == null){
            model = new Model();
        }
        return model;
    }

    //Hardcoded for now until database is added.
    public User user = new User("John Smith", "john.smith@gmail.com", "+1 (208) 956-2533");

    public User getUser(){
        return user;
    }

    //Modify user information
    public void changeUserUsername(String username){
        user.setUsername(username);
        contactObservers();
    }

    public void changeUserEmailAddress(String emailAddress){
        user.setEmailAddress(emailAddress);
        contactObservers();
    }

    public void changeUserPhoneNumber(String phoneNumber){
        user.setPhoneNumber(phoneNumber);
        contactObservers();
    }

    private void contactObservers(){
        setChanged();
        notifyObservers();
    }

}
