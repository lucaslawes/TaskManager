/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.model;

import java.util.Comparator;

/**
 *
 * @author lucas
 */
public class TaskByAssigneeDescendingComparator implements Comparator<Task> {
  
    @Override
    public int compare(Task firstTask, Task secondTask) {
       return secondTask.getAssignee().compareTo(firstTask.getAssignee());
    }
}
