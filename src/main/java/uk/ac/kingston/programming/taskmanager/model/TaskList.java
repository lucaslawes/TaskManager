package uk.ac.kingston.programming.taskmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lucas
 */
public class TaskList {
    @SerializedName("tasks")
    @Expose        
    private final ArrayList<Task> tasks = new ArrayList<>();

    public Task addTask(Task task) {
        this.tasks.add(task);
        return task;
    }
    
    public ArrayList<String> getAssigneeList(boolean selectedOnly) {
        ArrayList<String> assigneeList = new ArrayList<>();
        for(Task task : getTasks()) {
            if(!assigneeList.contains(task.getAssignee()) && (!selectedOnly || task.isSelected())) {
                assigneeList.add(task.getAssignee());
            }
        }
        Collections.sort(assigneeList);
        return assigneeList;
    }
    
    /**
     * @return the tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }     

    public void removeTask(Task task) {
        tasks.remove(task);
    }
    
}
