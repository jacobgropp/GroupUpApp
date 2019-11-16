package com.example.jakeg.groupupapp.activity.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jakeg on 11/15/2019.
 */

public class Model {

    public static Model model;

    public static Model getModel() {
        if (model == null){
            model = new Model();
        }
        return model;
    }

    public Map<String, User> users = new HashMap<String, User>();

    public Map<String, Group> groups = new HashMap<String, Group>();

    public void addUser(User user){
        users.put(user.getUserID(), user);
    }

    public void addGroup(Group group){
        groups.put(group.getGroupID(), group);
    }
}
