/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.listener;

import uk.ac.kingston.programming.todomanager.model.Task;
import uk.ac.kingston.programming.todomanager.model.TaskQuery;
import uk.ac.kingston.programming.todomanager.model.User;

/**
 *
 * @author lucas
 */
public interface TaskListListener {
    public void onLogin(User user);
    public void onLogout();    
    public void onTasksUpdated();   
    public void onTaskSelected(Task task);
    public void onTaskAdded(Task task);
    public void onTaskDeleted(Task task);
    public void onTaskUpdated(Task task);
    public void onTaskQueryChanged(TaskQuery taskQuery);
}
