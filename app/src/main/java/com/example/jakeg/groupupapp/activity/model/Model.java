package com.example.jakeg.groupupapp.activity.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

    public Boolean userChangeListener;

    //Hardcoded for now until database is added.
    public User user = new User("John Smith", "john.smith@gmail.com", "+1 (208) 956-2533");

    public Map<String, Group> groups = new HashMap<String, Group>();

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
