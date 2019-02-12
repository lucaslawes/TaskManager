/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.controller;

import uk.ac.kingston.programming.taskmanager.data.TaskListManager;
import uk.ac.kingston.programming.taskmanager.listener.TaskFormDialogListener;
import uk.ac.kingston.programming.taskmanager.model.Task;
import uk.ac.kingston.programming.taskmanager.view.AppView;
import uk.ac.kingston.programming.taskmanager.view.TaskFormDialog;

/**
 *
 * @author lucas
 */
public final class TaskFormDialogController implements TaskFormDialogListener{

    @Override
    public void onAddTask(Task task) {
        TaskListManager.getInstance().addTask(task);
        AppView.getInstance().setEnabled(true);
        TaskFormDialog.getInstance().dispose();
    }

    @Override
    public void onEditTask(Task task) {
        TaskListManager.getInstance().fireOnTaskUpdated(task);
        AppView.getInstance().setEnabled(true);
        TaskFormDialog.getInstance().dispose();
        
    }

    @Override
    public void onCancelButtonClicked() {
        AppView.getInstance().setEnabled(true);
        TaskFormDialog.getInstance().dispose();
    }
    
}
