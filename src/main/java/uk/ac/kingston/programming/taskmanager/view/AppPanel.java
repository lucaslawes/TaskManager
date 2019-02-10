/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.view;

import javax.swing.JPanel;
import uk.ac.kingston.programming.taskmanager.controller.TaskListListener;
import uk.ac.kingston.programming.taskmanager.controller.TaskListProvider;
import uk.ac.kingston.programming.taskmanager.model.Task;
import uk.ac.kingston.programming.taskmanager.model.User;

/**
 *
 * @author lucas
 */
public abstract class AppPanel extends JPanel implements TaskListListener {
    
    private TaskListProvider taskListProvider;
    
    public AppPanel(boolean isDoubleBuffered, boolean opaque) {
        super(isDoubleBuffered);
        setOpaque(opaque);
    }

    public void redraw() {
        removeAll();
        createChildControls();
        onTaskListProviderSet();
        updateUI();
    }
    
    public abstract void createChildControls();
    
    /**
     * @return the taskListProvider
     */
    public TaskListProvider getTaskListProvider() {
        return taskListProvider;
    }

    /**
     * @param taskListProvider the taskListProvider to set
     */
    public void setTaskListProvider(TaskListProvider taskListProvider) {
        this.taskListProvider = taskListProvider;
        onTaskListProviderSet();
    }
    
    public void onTaskListProviderSet() {
        
    };
    
    @Override
    public void onLogin(User user) {

    }

    @Override
    public void onLogout() {

    }
    
    @Override
    public void onTasksUpdated() {

    }

    @Override
    public void onTaskAdded(Task task) {

    }
    
    @Override
    public void onTaskSelected(Task task) {

    }

    @Override
    public void onTaskDeleted(Task task) {

    }

    @Override
    public void onTaskUpdated(Task task) {

    }
    
}
