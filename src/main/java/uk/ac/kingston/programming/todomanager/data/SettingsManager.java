/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.data;

import java.io.File;
import java.util.ArrayList;
import uk.ac.kingston.programming.todomanager.model.AppException;
import uk.ac.kingston.programming.todomanager.model.Settings;
import uk.ac.kingston.programming.todomanager.model.TaskList;
import uk.ac.kingston.programming.todomanager.model.User;

/**
 *
 * @author lucas
 */
public final class SettingsManager {
    
    private final static String SETTINGS_FILENAME = "settings.json";
    private final static String USER_FILENAME_PREFIX = ".json";
    
    private Settings settings = null;
    
    private static SettingsManager instance = null;
    
    public static SettingsManager getInstance() {
        if(instance == null) instance = new SettingsManager();
        return instance;
    }
    
    private SettingsManager() {
        load();
    }
    
    public void load(){
        settings = JsonFileManager.fromJson(SETTINGS_FILENAME, Settings.class);
    }

    public void save(){
        JsonFileManager.toJson(SETTINGS_FILENAME, settings);
    }
        
    public Settings getSettings(){
        return settings;
    }
    
    public User getUserByEmailAddress(String emailAddress) {
        ArrayList<User> users = settings.getUsers();
        for(User user : users) {
            if(user.getEmailAddress().equalsIgnoreCase(emailAddress)) {
                return user;
            }
        }
        return null;
    }
    
    public String getUserFilename(User user) {
        return user.getName() + USER_FILENAME_PREFIX;
    }
    
    public TaskList openTaskList(User user) {
        String userFilename = getUserFilename(user);
        File dataFile = new File(userFilename);
        if(!dataFile.exists()) {
            return null;
        }
        return JsonFileManager.fromJson(userFilename, TaskList.class);
    }

    public void createTaskListAndSave(User user) {
        String userFilename = getUserFilename(user);
        File dataFile = new File(userFilename);
        if(!dataFile.exists()) {
            JsonFileManager.toJson(userFilename, new TaskList());
        }
        else {
            throw new AppException("User data file already exists");
        }
    }
    
    public void registerUser(User user) {
        User existingUser = getUserByEmailAddress(user.getEmailAddress());
        if(existingUser != null) {
            throw new AppException("User already exists");
        }
        settings.getUsers().add(user);
        save();
        String userFilename = getUserFilename(user);
        File dataFile = new File(userFilename);
        if(dataFile.exists()) {
            throw new AppException("User data file already exists");
        }
        JsonFileManager.toJson(userFilename, new TaskList());        
    }
    
    public void saveTaskList(User user, TaskList taskList) {
        String userFilename = getUserFilename(user);
        JsonFileManager.toJson(userFilename, taskList);        
    }
}
