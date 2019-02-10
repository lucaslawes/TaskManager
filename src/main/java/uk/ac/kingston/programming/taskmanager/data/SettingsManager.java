/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.data;

import java.io.File;
import java.util.ArrayList;
import uk.ac.kingston.programming.taskmanager.model.AppException;
import uk.ac.kingston.programming.taskmanager.model.Settings;
import uk.ac.kingston.programming.taskmanager.model.TaskList;
import uk.ac.kingston.programming.taskmanager.model.User;

/**
 *
 * @author lucas
 */
public class SettingsManager {
    
    private final static String SETTINGS_FILENAME = "settings.json";
    private final static String USER_FILENAME_PREFIX = ".json";
    
    private static Settings settings = null;
    
    public static void load(){
        settings = JsonFileManager.fromJson(SETTINGS_FILENAME, Settings.class);
    }

    public static void save(){
        JsonFileManager.toJson(SETTINGS_FILENAME, settings);
    }
        
    public static Settings getSettings(){
        return settings;
    }
    
    public static User getUserByEmailAddress(String emailAddress) {
        ArrayList<User> users = settings.getUsers();
        for(User user : users) {
            if(user.getEmailAddress().equalsIgnoreCase(emailAddress)) {
                return user;
            }
        }
        return null;
    }
    
    public static String getUserFilename(User user) {
        return user.getName() + USER_FILENAME_PREFIX;
    }
    
    public static TaskList openTaskList(User user) {
        String userFilename = getUserFilename(user);
        File dataFile = new File(userFilename);
        if(!dataFile.exists()) {
            return null;
        }
        return JsonFileManager.fromJson(userFilename, TaskList.class);
    }

    public static void createTaskListAndSave(User user) {
        String userFilename = getUserFilename(user);
        File dataFile = new File(userFilename);
        if(!dataFile.exists()) {
            JsonFileManager.toJson(userFilename, new TaskList());
        }
        else {
            throw new AppException("User data file already exists");
        }
    }
    
    public static void registerUser(User user) {
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
    
    public static void saveTaskList(User user, TaskList taskList) {
        String userFilename = getUserFilename(user);
        JsonFileManager.toJson(userFilename, taskList);        
    }
}
