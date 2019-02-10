/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author lucas
 */
public class Settings {
    private String title = "";
    private String version = "";
    private String authors = "";
    private ArrayList<User> users = new ArrayList<>();

    public Settings() {
        
    }
    
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
    
    public boolean userIsUnique(User user) {
        for(User existingUser : this.getUsers()) {
            if(existingUser.getName().equalsIgnoreCase(user.getName()) || existingUser.getEmailAddress().equalsIgnoreCase(user.getEmailAddress())) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @return the authors
     */
    public String getAuthors() {
        return authors;
    }
}
