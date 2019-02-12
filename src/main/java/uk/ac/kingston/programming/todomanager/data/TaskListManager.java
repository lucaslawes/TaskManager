/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.data;

import java.util.ArrayList;
import java.util.Collections;
import uk.ac.kingston.programming.todomanager.listener.TaskListListener;
import uk.ac.kingston.programming.todomanager.model.AppException;
import uk.ac.kingston.programming.todomanager.model.Settings;
import uk.ac.kingston.programming.todomanager.model.Task;
import uk.ac.kingston.programming.todomanager.model.TaskByAssigneeAscendingComparator;
import uk.ac.kingston.programming.todomanager.model.TaskByAssigneeDescendingComparator;
import uk.ac.kingston.programming.todomanager.model.TaskByPriorityAscendingComparator;
import uk.ac.kingston.programming.todomanager.model.TaskByPriorityDescendingComparator;
import uk.ac.kingston.programming.todomanager.model.TaskByTargetDateAscendingComparator;
import uk.ac.kingston.programming.todomanager.model.TaskByTargetDateDescendingComparator;
import uk.ac.kingston.programming.todomanager.model.TaskByTitleAscendingComparator;
import uk.ac.kingston.programming.todomanager.model.TaskByTitleDescendingComparator;
import uk.ac.kingston.programming.todomanager.model.TaskList;
import uk.ac.kingston.programming.todomanager.model.TaskQuery;
import uk.ac.kingston.programming.todomanager.model.User;

/**
 *
 * @author lucas
 */
public final class TaskListManager{

    private User user = new User();
    
    private TaskList taskList = new TaskList();
 
    private final TaskQuery defaultTaskQuery = new TaskQuery("", TaskQuery.SORT_BY_TARGET_DATE, TaskQuery.SORT_ORDER_DESC, TaskQuery.FILTER_FIELD_STATUS, TaskQuery.FILTER_BY_ALL);
    
    private final ArrayList<TaskListListener> taskListListeners = new ArrayList<>();
    
    private static TaskListManager instance = null;
    
    public static TaskListManager getInstance() {
        if(instance == null) instance = new TaskListManager();
        return instance;
    }
    
    private TaskListManager() {
    }

    public boolean loginUser(String emailAddress, String password) {
        user = SettingsManager.getInstance().getUserByEmailAddress(emailAddress);
        if(getUser() == null) {
            throw new AppException("Invalid email address");
        }
        if(!user.getPassword().equals(password)){
            user = null;
            throw new AppException("Invalid password");
        }
        taskList = SettingsManager.getInstance().openTaskList(getUser());
        if(taskList == null) {
            user = null;
            throw new AppException("Task list not found");
        }
        taskList.getTasks().forEach((task) -> {
            task.setSelected(true);
        });
                
        taskListListeners.forEach((taskListListener) -> {
            taskListListener.onLogin(getUser());
        }); 
        
        return true;
    }

    public User getUser() {
        return user;
    }
    
    public void logoutUser() {
        taskList = new TaskList();
        user = new User();        
        taskListListeners.forEach((taskListListener) -> {
            taskListListener.onLogout();
        });         
    }
    
    public void saveSettings() {
        SettingsManager.getInstance().save();
    }

    public Settings getSettings() {
        return SettingsManager.getInstance().getSettings();
    }
    
    public void newTasks() {
        taskList = new TaskList();
        taskListListeners.forEach((taskListListener) -> {
            taskListListener.onTasksUpdated();
        });        
    }
    
    public void saveTasks() {
        SettingsManager.getInstance().saveTaskList(getUser(), taskList);
    }
    
    public void importTasks(String filename) {
        taskList = JsonFileManager.fromJson(filename, TaskList.class);
        
        if(taskList == null) {
            taskList = new TaskList();
        }
        
        taskList.getTasks().forEach((task) -> {
            task.setSelected(true);
        });

        queryTasks(getDefaultTaskQuery(), true);
        
        taskListListeners.forEach((taskListListener) -> {
            taskListListener.onTasksUpdated();
        });        
    }

    public void exportTasks(String folderPath) {
        String saveAsFilename = folderPath + "\\" + SettingsManager.getInstance().getUserFilename(getUser());
        JsonFileManager.toJson(saveAsFilename, taskList);
    }    

    public TaskList getTaskList() {
        return taskList;
    }

    public void queryTasks(TaskQuery taskQuery, boolean updateUI) {
        
        ArrayList<Task> tasks = taskList.getTasks();
                
        String textToSearch = taskQuery.getSearchText().toLowerCase();
        
        tasks.forEach((task) -> {
            task.setSelected(textToSearch.length() == 0 ? true : task.toString().toLowerCase().contains(textToSearch));
        });

        tasks.forEach((task) -> {
            if(task.isSelected()) {
                switch(taskQuery.getFilterTitle()) {
                    case TaskQuery.FILTER_FIELD_STATUS:
                        task.setSelected(taskQuery.getFilterLink().equals(TaskQuery.FILTER_BY_ALL) ? true : 
                                taskQuery.getFilterLink().equals(TaskQuery.FILTER_BY_ACTIVE) ? !task.isCompleted() : task.isCompleted());
                        break;
                    case TaskQuery.FILTER_FIELD_PRIORITY:
                        task.setSelected(taskQuery.getFilterLink().equals(TaskQuery.FILTER_BY_ALL) ? true : 
                                taskQuery.getFilterLink().equals(TaskQuery.FILTER_BY_HIGH) ? task.getPriority() == 1: taskQuery.getFilterLink().equals(TaskQuery.FILTER_BY_MEDIUM) ? task.getPriority() == 2 : task.getPriority() == 3);
                        break;
                    case TaskQuery.FILTER_FIELD_TARGET_DATE:
                        task.setSelected(taskQuery.getFilterLink().equals(TaskQuery.FILTER_BY_ALL) ? true : 
                                taskQuery.getFilterLink().equals(TaskQuery.FILTER_BY_TODAY) ? task.isDueToday(): taskQuery.getFilterLink().equals(TaskQuery.FILTER_BY_THIS_WEEK) ? task.isDueThisWeek() : taskQuery.getFilterLink().equals(TaskQuery.FILTER_BY_THIS_MONTH) ? task.isDueThisMonth() : task.isDueNextMonth());
                        break;
                    case TaskQuery.FILTER_FIELD_ASSIGNEE:
                        task.setSelected(taskQuery.getFilterLink().equals(TaskQuery.FILTER_BY_ALL) ? true : 
                                taskQuery.getFilterLink().equals(task.getAssignee()));
                        break;
                }                
            }
        });
        
        if(taskQuery.getSortOrder().equals(TaskQuery.SORT_ORDER_ASC)) {
            switch(taskQuery.getSortBy()) 
            {
                case TaskQuery.SORT_BY_TITLE:
                    Collections.sort(taskList.getTasks(), new TaskByTitleAscendingComparator());
                    break;
                case TaskQuery.SORT_BY_ASSIGNEE:
                    Collections.sort(taskList.getTasks(), new TaskByAssigneeAscendingComparator());
                    break;
                case TaskQuery.SORT_BY_PRIORITY:
                    Collections.sort(taskList.getTasks(), new TaskByPriorityAscendingComparator());
                    break;
                case TaskQuery.SORT_BY_TARGET_DATE:
                    Collections.sort(taskList.getTasks(), new TaskByTargetDateAscendingComparator());
                    break;                    
            }
        }
        else {
            switch(taskQuery.getSortBy()) 
            {
                case TaskQuery.SORT_BY_TITLE:
                    Collections.sort(taskList.getTasks(), new TaskByTitleDescendingComparator());
                    break;
                case TaskQuery.SORT_BY_ASSIGNEE:
                    Collections.sort(taskList.getTasks(), new TaskByAssigneeDescendingComparator());
                    break;
                case TaskQuery.SORT_BY_PRIORITY:
                    Collections.sort(taskList.getTasks(), new TaskByPriorityDescendingComparator());
                    break;
                case TaskQuery.SORT_BY_TARGET_DATE:
                    Collections.sort(taskList.getTasks(), new TaskByTargetDateDescendingComparator());
                    break;                    
            }
        }        
        
        taskListListeners.forEach((taskListListener) -> {
            taskListListener.onTasksUpdated();
            if(updateUI) {
                taskListListener.onTaskQueryChanged(taskQuery);
            }
        });        
    }
    
    public void fireOnTaskSelected(Task task) {
        taskListListeners.forEach((taskListListener) -> {
            taskListListener.onTaskSelected(task);
        }); 
    }
        
    public void addTask(Task task) {
        taskList.addTask(task);
        taskListListeners.forEach((taskListListener) -> {
            taskListListener.onTaskAdded(task);
        });        
    }
       
    public void deleteTask(Task task) {
        taskList.removeTask(task);
        taskListListeners.forEach((taskListListener) -> {
            taskListListener.onTaskDeleted(task);
        });        
    }

    public void fireOnTaskUpdated(Task task) {
        taskListListeners.forEach((taskListListener) -> {
            taskListListener.onTaskUpdated(task);
        });                
    }
    
    public void addTaskListListener(TaskListListener taskListListener) {
        if(!taskListListeners.contains(taskListListener)){
            taskListListeners.add(taskListListener);
        }
    } 

    /**
     * @return the defaultTaskQuery
     */
    public TaskQuery getDefaultTaskQuery() {
        return defaultTaskQuery;
    }
}
