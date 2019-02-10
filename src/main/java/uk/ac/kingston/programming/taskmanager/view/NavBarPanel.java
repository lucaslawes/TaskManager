/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.view;

import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import uk.ac.kingston.programming.taskmanager.controller.NavBarLinkListener;
import uk.ac.kingston.programming.taskmanager.model.Task;
import uk.ac.kingston.programming.taskmanager.model.User;

/**
 *
 * @author lucas
 */
public final class NavBarPanel extends AppPanel implements NavBarLinkListener{

    private NavBarLinksPanel statusNavBarLinksPanel;
    private NavBarLinksPanel priorityNavBarLinksPanel;
    private NavBarLinksPanel dueDateNavBarLinksPanel;
    private NavBarLinksPanel assigneeNavBarLinksPanel;
    
    public NavBarPanel() {
        
        super(false, true);

        createChildControls();
    }
    
    @Override
    public void createChildControls() {
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Styling.createColor(700));
        
        setBorder(BorderFactory.createEmptyBorder(20, 60, 0, 0));
        
        statusNavBarLinksPanel = new NavBarLinksPanel("Status", new String[] { "All", "Active", "Completed"});
        add(statusNavBarLinksPanel);
        
        priorityNavBarLinksPanel = new NavBarLinksPanel("Priority", new String[] { "All", "High", "Medium", "Low"});
        add(priorityNavBarLinksPanel);  
        
        dueDateNavBarLinksPanel = new NavBarLinksPanel("Due Date", new String[] { "All", "Today", "This Week", "This Month", "Next Month"});
        add(dueDateNavBarLinksPanel);        
        
        assigneeNavBarLinksPanel = new NavBarLinksPanel("Assigned To", new String[] { "All"});
        add(assigneeNavBarLinksPanel);        
    }
    
    @Override
    public void onTaskListProviderSet() {
        getTaskListProvider().addTaskListListener(this);
        statusNavBarLinksPanel.setNavbarLinkListener(this);
        priorityNavBarLinksPanel.setNavbarLinkListener(this);
        dueDateNavBarLinksPanel.setNavbarLinkListener(this);
        assigneeNavBarLinksPanel.setNavbarLinkListener(this);
    }    

    @Override
    public void onNavbarLinkClicked(String title, String link) {
        getTaskListProvider().filterTasks(title, link);
    }
    
    @Override
    public void onLogin(User user) {
        refresh();
    }

    @Override
    public void onLogout() {
        refresh();
    }
    
    @Override
    public void onTasksUpdated() {
        refresh();
    }

    @Override
    public void onTaskAdded(Task task) {
        refresh();
    }
    
    @Override
    public void onTaskSelected(Task task) {
        refresh();
    }

    @Override
    public void onTaskDeleted(Task task) {
        refresh();
    }

    @Override
    public void onTaskUpdated(Task task) {
        refresh();
    }    
    
    private void refresh() {
        
        remove(assigneeNavBarLinksPanel);
        
        ArrayList<String> assigneeList = getTaskListProvider().getTaskList().getAssigneeList(true);
        
        assigneeList.add(0, "All");
        
        String[] assignees = assigneeList.stream().toArray(String[]::new);
        
        if(assigneeList.size() > 6) {
            assignees = assigneeList.stream().limit(6).collect(Collectors.toList()).toArray(new String[6]);
        }
        
        assigneeNavBarLinksPanel = new NavBarLinksPanel("Assigned To", assignees);
        assigneeNavBarLinksPanel.setNavbarLinkListener(this);
        add(assigneeNavBarLinksPanel);
        
        updateUI();
    }
}
