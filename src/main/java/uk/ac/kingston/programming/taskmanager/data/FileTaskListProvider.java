/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.data;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import uk.ac.kingston.programming.taskmanager.controller.AppDialogListener;
import uk.ac.kingston.programming.taskmanager.controller.StatusBarListener;
import uk.ac.kingston.programming.taskmanager.controller.TaskListListener;
import uk.ac.kingston.programming.taskmanager.model.Task;
import uk.ac.kingston.programming.taskmanager.model.TaskList;
import uk.ac.kingston.programming.taskmanager.controller.TaskListProvider;
import uk.ac.kingston.programming.taskmanager.model.AppException;
import uk.ac.kingston.programming.taskmanager.model.Settings;
import uk.ac.kingston.programming.taskmanager.model.TaskByAssigneeAscendingComparator;
import uk.ac.kingston.programming.taskmanager.model.TaskByAssigneeDescendingComparator;
import uk.ac.kingston.programming.taskmanager.model.TaskByPriorityAscendingComparator;
import uk.ac.kingston.programming.taskmanager.model.TaskByPriorityDescendingComparator;
import uk.ac.kingston.programming.taskmanager.model.TaskByTargetDateAscendingComparator;
import uk.ac.kingston.programming.taskmanager.model.TaskByTargetDateDescendingComparator;
import uk.ac.kingston.programming.taskmanager.model.TaskByTitleAscendingComparator;
import uk.ac.kingston.programming.taskmanager.model.TaskByTitleDescendingComparator;
import uk.ac.kingston.programming.taskmanager.model.TaskQuery;
import uk.ac.kingston.programming.taskmanager.model.User;

/**
 *
 * @author lucas
 */
public class FileTaskListProvider implements TaskListProvider{
   
    private User user = new User();
    
    private TaskList taskList = new TaskList();
    
    private ArrayList<TaskListListener> taskListListeners = new ArrayList<>();
    
    private AppDialogListener appDialogListener;
    private StatusBarListener statusBarListener;

    private TaskQuery taskQuery = new TaskQuery();
    
    public FileTaskListProvider() {
//        this.taskList = TaskListAutoGenerator.CreateDummyTaskList();
//        ArrayList<Task> tasks = taskList.getTasks();
//        tasks.forEach((task) -> {
//            task.setSelected(true);
//        });
    }

    public boolean loginUser(String emailAddress, String password) {
        user = SettingsManager.getUserByEmailAddress(emailAddress);
        if(getUser() == null) {
            throw new AppException("Invalid email address");
        }
        if(!user.getPassword().equals(password)){
            user = null;
            throw new AppException("Invalid password");
        }
        taskList = SettingsManager.openTaskList(getUser());
        if(taskList == null) {
            user = null;
            throw new AppException("Task list not found");
        }
        taskList.getTasks().forEach((task) -> {
            task.setSelected(true);
        });
        
        taskListListeners.forEach((taskListListener) -> {
            taskListListener.onLogin(getUser());
            taskListListener.onTasksUpdated();
        }); 
        return true;
    }
    
    /**
     * @return the user
     */
    @Override
    public User getUser() {
        return user;
    }
    
    @Override
    public void logoutUser() {
        taskList = new TaskList();
        user = new User();
        taskListListeners.forEach((taskListListener) -> {
            taskListListener.onLogout();
        });         
    }

    @Override
    public Settings getSettings() {
        return SettingsManager.getSettings();
    }
    
    @Override
    public void saveSettings() {
        SettingsManager.save();
    }
    
    @Override
    public void fireOnTasksUpdated() {
        taskListListeners.forEach((taskListListener) -> {
            taskListListener.onTasksUpdated();
        });
    }
    
    @Override
    public void newTasks() {
        taskList = new TaskList();
        fireOnTasksUpdated();
    }
    
    @Override
    public void saveTasks() {
        SettingsManager.saveTaskList(getUser(), taskList);
    }
    
    @Override
    public void importTasks(String filename) {
        taskList = JsonFileManager.fromJson(filename, TaskList.class);
        
        if(taskList == null) {
            taskList = new TaskList();
        }
        
        taskList.getTasks().forEach((task) -> {
            task.setSelected(true);
        });

        fireOnTasksUpdated();
    }

    @Override
    public void exportTasks(String folderPath) {
        String saveAsFilename = folderPath + "\\" + SettingsManager.getUserFilename(getUser());
        JsonFileManager.toJson(saveAsFilename, taskList);
    }    

    public TaskList getTaskList() {
        return taskList;
    }
    
    public TaskQuery getTaskQuery() {
        return taskQuery;
    }    
    
    public void addTaskListListener(TaskListListener listener) {
        taskListListeners.add(listener);
    }    

    @Override
    public void searchTaskList(String searchText) {
        ArrayList<Task> tasks = taskList.getTasks();
        
        getTaskQuery().setSearchText(searchText);
        
        String textToSearch = searchText.toLowerCase();
        
        tasks.forEach((task) -> {
            task.setSelected(textToSearch.length() == 0 ? true : task.toString().toLowerCase().contains(textToSearch));
        });

        fireOnTasksUpdated();
    }
    
    @Override
    public void sortTasks(String sortBy, String sortOrder) {
        
        getTaskQuery().setSortBy(sortBy);
        getTaskQuery().setSortOrder(sortOrder);
                
        if(sortOrder.equals("Ascending")) {
            switch(sortBy) 
            {
                case "Title":
                    Collections.sort(taskList.getTasks(), new TaskByTitleAscendingComparator());
                    break;
                case "Assignee":
                    Collections.sort(taskList.getTasks(), new TaskByAssigneeAscendingComparator());
                    break;
                case "Priority":
                    Collections.sort(taskList.getTasks(), new TaskByPriorityAscendingComparator());
                    break;
                case "Due Date":
                    Collections.sort(taskList.getTasks(), new TaskByTargetDateAscendingComparator());
                    break;                    
            }
        }
        else {
            switch(sortBy) 
            {
                case "Title":
                    Collections.sort(taskList.getTasks(), new TaskByTitleDescendingComparator());
                    break;
                case "Assignee":
                    Collections.sort(taskList.getTasks(), new TaskByAssigneeDescendingComparator());
                    break;
                case "Priority":
                    Collections.sort(taskList.getTasks(), new TaskByPriorityDescendingComparator());
                    break;
                case "Due Date":
                    Collections.sort(taskList.getTasks(), new TaskByTargetDateDescendingComparator());
                    break;                    
            }
        }
        
        fireOnTasksUpdated();
    }

    @Override
    public void filterTasks(String title, String link) {

        getTaskQuery().setFilterTitle(title);
        getTaskQuery().setFilterLink(link);
        
        ArrayList<Task> tasks = taskList.getTasks();
        switch(title) {
            case "Status":
                tasks.forEach((task) -> {
                    task.setSelected((getTaskQuery().getSearchText().length() == 0 ? true : task.toString().toLowerCase().contains(getTaskQuery().getSearchText().toLowerCase())) && 
                            (link.equals("All") ? true : link.equals("Active") ? !task.isCompleted() : task.isCompleted()));
        });                
                break;
            case "Priority":
                tasks.forEach((task) -> {
                    task.setSelected((getTaskQuery().getSearchText().length() == 0 ? true : task.toString().toLowerCase().contains(getTaskQuery().getSearchText().toLowerCase())) && 
                            (link.equals("All") ? true : link.equals("High") ? task.getPriority() == 1: link.equals("Medium") ? task.getPriority() == 2 : task.getPriority() == 3));
        });                  
                break;
            case "Due Date":
                tasks.forEach((task) -> {
                    task.setSelected((getTaskQuery().getSearchText().length() == 0 ? true : task.toString().toLowerCase().contains(getTaskQuery().getSearchText().toLowerCase())) && 
                            (link.equals("All") ? true : link.equals("Today") ? task.isDueToday(): link.equals("This Week") ? task.isDueThisWeek() : link.equals("This Month") ? task.isDueThisMonth() : task.isDueNextMonth()));
                
        });     
                break;
            case "Assigned To":
                tasks.forEach((task) -> {
                    task.setSelected((getTaskQuery().getSearchText().length() == 0 ? true : task.toString().toLowerCase().contains(getTaskQuery().getSearchText().toLowerCase())) && 
                            (link.equals("All") ? true : link.equals(task.getAssignee())));
        });
                break;                                
                
        }
        
        fireOnTasksUpdated();
    }
    
    @Override
    public void setStatusBarText(String text){
        if(statusBarListener != null){
            statusBarListener.setStatusBarText(text);
        }
    }

    @Override
    public void selectTask(Task task) {
        taskListListeners.forEach((taskListListener) -> {
            taskListListener.onTaskSelected(task);
        });          
    }

    
    @Override
    public void addTask() {
        if(appDialogListener != null) {
            appDialogListener.showNewTaskFormDialog();
        }
    }

    @Override
    public void addTaskCompleted(Task task) {
        taskList.addTask(task);
        taskListListeners.forEach((taskListListener) -> {
            taskListListener.onTaskAdded(task);
        });
    }
    
    @Override
    public void editTask(Task task) {
        if(appDialogListener != null) {
            appDialogListener.showEditTaskFormDialog(task);
        }
    }

    @Override
    public void editTaskCompleted(Task task) {
        fireOnTasksUpdated();
    }
    
    @Override
    public void deleteTask(Task task) {
        taskList.removeTask(task);
        taskListListeners.forEach((taskListListener) -> {
            taskListListener.onTaskDeleted(task);
        }); 
    }

    /**
     * @param statusBarListener the statusBarListener to set
     */
    @Override
    public void setStatusBarListener(StatusBarListener statusBarListener) {
        this.statusBarListener = statusBarListener;
    }
    
    /**
     * @param appDialogListener the appDialogListener to set
     */
    public void setAppDialogListener(AppDialogListener appDialogListener) {
        this.appDialogListener = appDialogListener;
    }
}
