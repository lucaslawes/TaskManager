/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.listener;

import uk.ac.kingston.programming.taskmanager.model.Task;

/**
 *
 * @author lucas
 */
public interface TaskFormDialogListener {
    public void onAddTask(Task task);
    public void onEditTask(Task task);
    public void onCancelButtonClicked();
}
