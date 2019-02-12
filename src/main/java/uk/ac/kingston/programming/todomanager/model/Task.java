/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;


/**
 *
 * @author lucas
 */
public class Task extends TaskItem{
    
    @SerializedName("assignee")
    @Expose    
    private String assignee;
    
    @SerializedName("targetDate")
    @Expose
    private LocalDate targetDate = LocalDate.now();

    @SerializedName("priority")
    @Expose    
    private int priority = 3;
    
    @SerializedName("subTasks")
    @Expose
    private final ArrayList<SubTask> subTasks = new ArrayList<>();
    
    public Task() {
        
    }
    
    public Task(int id, String title, String assignee, LocalDate targetDate) {
        super(id, title);
        this.assignee = assignee;
        this.targetDate = targetDate;
    }

    public Task(int id, String title, String assignee, LocalDate targetDate, SubTask[] subTasks) {
        super(id, title);
        this.assignee = assignee;
        this.targetDate = targetDate;
        this.subTasks.addAll(Arrays.asList(subTasks));
    }
    
    /**
     * @return the assignee
     */
    public String getAssignee() {
        return assignee;
    }

    /**
     * @param assignee the assignee to set
     */
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    /**
     * @return the targetDate
     */
    public LocalDate getTargetDate() {
        return targetDate;
    }
    
    public String getTargetDateAsString() {
        return this.getTargetDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH));
    }    

    /**
     * @param targetDate the targetDate to set
     */
    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }
    
    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    } 

    /**
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    public boolean isDueToday() {
        return targetDate.equals(LocalDate.now());
    }
    
    public boolean isDueThisWeek() {
        LocalDate today = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.ENGLISH); 
        return targetDate.get(weekFields.weekOfWeekBasedYear()) == today.get(weekFields.weekOfWeekBasedYear());        
    }
    
    public boolean isDueThisMonth() {
        LocalDate today = LocalDate.now();
        return targetDate.getYear() != today.getYear() ? false : targetDate.getMonth() == today.getMonth();
    }

    public boolean isDueNextMonth() {
        LocalDate nextMonth = LocalDate.now().plusMonths(1);
        return targetDate.getYear() != nextMonth.getYear() ? false : targetDate.getMonth() == nextMonth.getMonth();
    }
    
    @Override 
    public String toString() {
        String formattedString = this.getTitle() + " (assigned to " + this.getAssignee() + ", target completion date " + this.getTargetDateAsString() + ", priority " + this.getPriority() + ")\n";
        return formattedString;
    }    
    
    public String toHtml(boolean strikethrough) {
        String html = "<html><body><span ";
        html += strikethrough ? "style='text-decoration: line-through;'" : "";
        html += "><b>" + this.getTitle() + "</b> (assigned to <b>" + this.getAssignee() + "</b>, target completion date <b>" + this.getTargetDateAsString() + "</b>, priority <b>" + this.getPriority() + "</b>)";
        html += "</span></body></html>";
        return html;        
    }
}
