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
import uk.ac.kingston.programming.taskmanager.listener.NavBarLinkListener;
import uk.ac.kingston.programming.taskmanager.model.TaskQuery;

/**
 *
 * @author lucas
 */
public final class NavBarPanel extends AppPanel {
    
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
        
        setBorder(BorderFactory.createEmptyBorder(25, 25, 0, 0));
        
        statusNavBarLinksPanel = new NavBarLinksPanel();
        add(getStatusNavBarLinksPanel());
        
        priorityNavBarLinksPanel = new NavBarLinksPanel();
        add(getPriorityNavBarLinksPanel());  
        
        dueDateNavBarLinksPanel = new NavBarLinksPanel();
        add(getDueDateNavBarLinksPanel());        
        
        assigneeNavBarLinksPanel = new NavBarLinksPanel();
        add(getAssigneeNavBarLinksPanel());        
    }    

    /**
     * @return the statusNavBarLinksPanel
     */
    public NavBarLinksPanel getStatusNavBarLinksPanel() {
        return statusNavBarLinksPanel;
    }

    /**
     * @return the priorityNavBarLinksPanel
     */
    public NavBarLinksPanel getPriorityNavBarLinksPanel() {
        return priorityNavBarLinksPanel;
    }

    /**
     * @return the dueDateNavBarLinksPanel
     */
    public NavBarLinksPanel getDueDateNavBarLinksPanel() {
        return dueDateNavBarLinksPanel;
    }

    /**
     * @return the assigneeNavBarLinksPanel
     */
    public NavBarLinksPanel getAssigneeNavBarLinksPanel() {
        return assigneeNavBarLinksPanel;
    }
    
    public void refresh(NavBarLinkListener navbarLinkListener, String assigneeTitle, ArrayList<String> assigneeList) {
        
        remove(assigneeNavBarLinksPanel);
        
        assigneeList.add(0, TaskQuery.FILTER_BY_ALL);
        
        String[] assignees = assigneeList.stream().toArray(String[]::new);
        
        if(assigneeList.size() > 6) {
            assignees = assigneeList.stream().limit(6).collect(Collectors.toList()).toArray(new String[6]);
        }
        
        assigneeNavBarLinksPanel = new NavBarLinksPanel();
        assigneeNavBarLinksPanel.setLinks(assigneeTitle, assignees);
        assigneeNavBarLinksPanel.setNavbarLinkListener(navbarLinkListener);
        add(assigneeNavBarLinksPanel);
        
        updateUI();
    }
    
    public void setSelected(NavBarLink selectedNavBarLink) {
        
        NavBarLinksPanel[] navBarLinksPanels = new NavBarLinksPanel[] {
            statusNavBarLinksPanel,
            priorityNavBarLinksPanel,
            dueDateNavBarLinksPanel,
            assigneeNavBarLinksPanel
        };
        
        for(NavBarLinksPanel navBarLinksPanel : navBarLinksPanels) {
            navBarLinksPanel.getNavBarLinks().forEach((navBarLink) -> {
                navBarLink.setSelected(navBarLink.getTitle().equals(selectedNavBarLink.getTitle()) &&
                        navBarLink.getLink().equals(selectedNavBarLink.getLink()));
            });
        }
    }
    
    public NavBarLink getNavBarLink(String title, String link) {
        
        NavBarLinksPanel[] navBarLinksPanels = new NavBarLinksPanel[] {
            statusNavBarLinksPanel,
            priorityNavBarLinksPanel,
            dueDateNavBarLinksPanel,
            assigneeNavBarLinksPanel
        };
        
        for(NavBarLinksPanel navBarLinksPanel : navBarLinksPanels) {
            for(NavBarLink navBarLink : navBarLinksPanel.getNavBarLinks()) {
                if(navBarLink.getTitle().equals(title) && navBarLink.getLink().equals(link)) {
                    return navBarLink;
                }
            }
        }        
        
        return null;
    }
}
