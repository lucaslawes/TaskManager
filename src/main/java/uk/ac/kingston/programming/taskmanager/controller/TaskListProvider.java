/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.controller;

import javax.swing.JFrame;
import uk.ac.kingston.programming.taskmanager.model.Settings;
import uk.ac.kingston.programming.taskmanager.model.Task;
import uk.ac.kingston.programming.taskmanager.model.TaskList;
import uk.ac.kingston.programming.taskmanager.model.TaskQuery;
import uk.ac.kingston.programming.taskmanager.model.User;

/**
 *
 * @author lucas
 */
public interface TaskListProvider {
    
    public User getUser();
    
    public Settings getSettings();
    
    public void saveSettings();
    
    public TaskList getTaskList();
    
    public TaskQuery getTaskQuery();
    
    public void fireOnTasksUpdated();
    
    public void setAppDialogListener(AppDialogListener appDialogListener);
    
    public void addTaskListListener(TaskListListener listener);
    
    public void setStatusBarListener(StatusBarListener listener);
    
    public void setStatusBarText(String text);
    
    public void searchTaskList(String searchText);
    
    public void saveTasks();
    
    public void newTasks();

    public void importTasks(String filename);

    public void exportTasks(String folderPath);
    
    public boolean loginUser(String emailAddress, String password);
    
    public void logoutUser();

    public void selectTask(Task task);

    public void addTask();
    
    public void addTaskCompleted(Task task);
    
    public void editTask(Task task);

    public void editTaskCompleted(Task task);    
    
    public void deleteTask(Task task);

    public void sortTasks(String sortBy, String sortOrder);

    public void filterTasks(String title, String link);

}
