/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.controller;

import uk.ac.kingston.programming.todomanager.data.TaskListManager;
import uk.ac.kingston.programming.todomanager.listener.TaskFormDialogListener;
import uk.ac.kingston.programming.todomanager.model.Task;
import uk.ac.kingston.programming.todomanager.view.AppView;
import uk.ac.kingston.programming.todomanager.view.TaskFormDialog;

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
