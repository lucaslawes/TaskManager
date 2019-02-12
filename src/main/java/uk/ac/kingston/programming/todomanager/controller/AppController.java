/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.controller;

import java.awt.Color;
import uk.ac.kingston.programming.todomanager.listener.JTextFieldChangeListener;
import uk.ac.kingston.programming.todomanager.listener.NavBarLinkListener;
import uk.ac.kingston.programming.todomanager.listener.TaskListListener;
import uk.ac.kingston.programming.todomanager.listener.InboxItemsPanelListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import uk.ac.kingston.programming.todomanager.data.TaskListManager;
import uk.ac.kingston.programming.todomanager.listener.PreviewPanelListener;
import uk.ac.kingston.programming.todomanager.model.Task;
import uk.ac.kingston.programming.todomanager.model.TaskQuery;
import uk.ac.kingston.programming.todomanager.model.User;
import uk.ac.kingston.programming.todomanager.view.AppView;
import uk.ac.kingston.programming.todomanager.view.NavBarLink;
import uk.ac.kingston.programming.todomanager.view.Styling;

/**
 *
 * @author lucas
 */
public final class AppController implements NavBarLinkListener, TaskListListener {
      
    private final TaskQuery taskQuery = new TaskQuery();
    
    public AppController() {
        Styling.setStylingListener(() -> TaskListManager.getInstance().getUser().getTheme());
        TaskListManager.getInstance().addTaskListListener(AppController.this);
    }
    
    private AppView getView() {
        return AppView.getInstance();
    }
    
    public void bind(boolean isRefresh) {
        
        getView().getTopBarPanel().getNewButton().addActionListener((ActionEvent e) -> {
            getView().showTaskFormDialog(null);
        });
        
        JTextFieldChangeListener.addChangeListener(getView().getTopBarPanel().getSearchField(), e -> {
            performQueryTasks(false);
        });    
        
        getView().getTopBarPanel().getSortByComboBox().addActionListener((ActionEvent e) -> {
            performQueryTasks(false);
        });        

        getView().getTopBarPanel().getSortOrderComboBox().addActionListener((ActionEvent e) -> {
            performQueryTasks(false);
        });
        
        getView().getTopBarPanel().getLogoutLabel().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TaskListManager.getInstance().logoutUser();
                getView().showLoginDialog();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
            
        });
                
        getView().getNavBarPanel().getStatusNavBarLinksPanel().setNavbarLinkListener(this);
        getView().getNavBarPanel().getStatusNavBarLinksPanel().setLinks(TaskQuery.FILTER_FIELD_STATUS, new String[] { TaskQuery.FILTER_BY_ALL, TaskQuery.FILTER_BY_ACTIVE, TaskQuery.FILTER_BY_COMPLETED});
        
        getView().getNavBarPanel().getPriorityNavBarLinksPanel().setNavbarLinkListener(this);
        getView().getNavBarPanel().getPriorityNavBarLinksPanel().setLinks(TaskQuery.FILTER_FIELD_PRIORITY, new String[] { TaskQuery.FILTER_BY_ALL, TaskQuery.FILTER_BY_HIGH, TaskQuery.FILTER_BY_MEDIUM, TaskQuery.FILTER_BY_LOW});
        
        getView().getNavBarPanel().getDueDateNavBarLinksPanel().setNavbarLinkListener(this);
        getView().getNavBarPanel().getDueDateNavBarLinksPanel().setLinks(TaskQuery.FILTER_FIELD_TARGET_DATE, new String[] { TaskQuery.FILTER_BY_ALL, TaskQuery.FILTER_BY_TODAY, TaskQuery.FILTER_BY_THIS_WEEK, TaskQuery.FILTER_BY_THIS_MONTH, TaskQuery.FILTER_BY_NEXT_MONTH});
        
        getView().getNavBarPanel().getAssigneeNavBarLinksPanel().setNavbarLinkListener(this);
        getView().getNavBarPanel().getAssigneeNavBarLinksPanel().setLinks(TaskQuery.FILTER_FIELD_ASSIGNEE, new String[] { TaskQuery.FILTER_BY_ALL});
        
        getView().getInboxPanel().getInboxItemsPanel().setInboxItemsPanelListener(new InboxItemsPanelListener() {
            @Override
            public void onTaskDeleteClicked(Task task) {
                if(JOptionPane.showConfirmDialog(getView(), "Do you want to delete task '" + task.getTitle() + "'?", "Delete Task", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                    return;
                }
                TaskListManager.getInstance().deleteTask(task);                
            }

            @Override
            public void onTaskEditClicked(Task task) {
                getView().showTaskFormDialog(task);
            }

            @Override
            public void onTaskSelectClicked(Task task) {
                TaskListManager.getInstance().fireOnTaskSelected(task);
            }

            @Override
            public void onSetStatusBarText(String text) {
                getView().getStatusBarPanel().setStatusBarText(text);
            }
        });
        
        getView().getPreviewPanel().setPreviewPanelListener(new PreviewPanelListener() {
            @Override
            public void onEditButtonClicked(Task task) {
                getView().showTaskFormDialog(task);
            }

            @Override
            public void onDeleteButtonClicked(Task task) {
                if(JOptionPane.showConfirmDialog(getView(), "Do you want to delete task '" + task.getTitle() + "'?", "Delete Task", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                    return;
                }
                TaskListManager.getInstance().deleteTask(task);                
            }

            @Override
            public void onTaskItemUpdated(Task task) {
                TaskListManager.getInstance().fireOnTaskUpdated(task);
            }
        });
        
        if(isRefresh) {
            TaskListManager.getInstance().queryTasks(TaskListManager.getInstance().getDefaultTaskQuery(), true);
            getView().setEnabled(true);        
            getView().getTopBarPanel().setUser(TaskListManager.getInstance().getUser());
            getView().getNavBarPanel().refresh(this, TaskQuery.FILTER_FIELD_ASSIGNEE, TaskListManager.getInstance().getTaskList().getAssigneeList(true));        
            getView().getInboxPanel().getInboxItemsPanel().refresh(TaskListManager.getInstance().getTaskList());
        }
    }

    @Override
    public void onNavBarLinkClicked(NavBarLink navBarLink, String title, String link) {
        taskQuery.setFilterTitle(title);
        taskQuery.setFilterLink(link);
        performQueryTasks(false);
        getView().getNavBarPanel().setSelected(navBarLink);
    } 

    @Override
    public void onLogin(User user) {
        getView().refresh();
        TaskListManager.getInstance().queryTasks(TaskListManager.getInstance().getDefaultTaskQuery(), true);
        getView().setEnabled(true);     
        getView().getAppMenuBar().getEditUsersMenuItem().setVisible(user.getIsAdministrator());
        getView().getTopBarPanel().setUser(user);
        getView().getNavBarPanel().refresh(this, TaskQuery.FILTER_FIELD_ASSIGNEE, TaskListManager.getInstance().getTaskList().getAssigneeList(true));        
        getView().getInboxPanel().getInboxItemsPanel().refresh(TaskListManager.getInstance().getTaskList());
    }

    @Override
    public void onLogout() {
        getView().getTopBarPanel().clearUser();
        getView().getNavBarPanel().refresh(this, TaskQuery.FILTER_FIELD_ASSIGNEE, TaskListManager.getInstance().getTaskList().getAssigneeList(true));
        getView().getInboxPanel().getInboxItemsPanel().refresh(TaskListManager.getInstance().getTaskList());
        getView().getPreviewPanel().refresh();
    }

    @Override
    public void onTasksUpdated() {
        getView().getNavBarPanel().refresh(this, TaskQuery.FILTER_FIELD_ASSIGNEE, TaskListManager.getInstance().getTaskList().getAssigneeList(true));
        getView().getInboxPanel().getInboxItemsPanel().refresh(TaskListManager.getInstance().getTaskList());
        getView().getPreviewPanel().refresh();
    }

    @Override
    public void onTaskSelected(Task task) {
        getView().getPreviewPanel().refresh(task);
    }

    @Override
    public void onTaskAdded(Task task) {
        performQueryTasks(false);
        getView().getNavBarPanel().refresh(this, TaskQuery.FILTER_FIELD_ASSIGNEE, TaskListManager.getInstance().getTaskList().getAssigneeList(true));
        getView().getInboxPanel().getInboxItemsPanel().refresh(TaskListManager.getInstance().getTaskList());
        getView().getPreviewPanel().refresh(task);
        getView().getInboxPanel().getInboxItemsPanel().setActive(task);        
    }

    @Override
    public void onTaskDeleted(Task task) {
        getView().getInboxPanel().getInboxItemsPanel().refresh(TaskListManager.getInstance().getTaskList());
        getView().getPreviewPanel().refresh();
    }

    @Override
    public void onTaskUpdated(Task task) {
        getView().getNavBarPanel().refresh(this, TaskQuery.FILTER_FIELD_ASSIGNEE, TaskListManager.getInstance().getTaskList().getAssigneeList(true));
        getView().getInboxPanel().getInboxItemsPanel().refresh(TaskListManager.getInstance().getTaskList());
        getView().getPreviewPanel().refresh(task);
    }

    @Override
    public void onTaskQueryChanged(TaskQuery taskQuery) {
        NavBarLink navBarLink = getView().getNavBarPanel().getNavBarLink(taskQuery.getFilterTitle(), taskQuery.getFilterLink());
        if(navBarLink != null) {
            getView().getNavBarPanel().setSelected(navBarLink);
        }
        getView().getTopBarPanel().getSearchField().setText(taskQuery.getSearchText());
        getView().getTopBarPanel().getSortByComboBox().setSelectedItem(taskQuery.getSortBy());
        getView().getTopBarPanel().getSortOrderComboBox().setSelectedItem(taskQuery.getSortOrder());
    }
    
    private void performQueryTasks(boolean updateUI) {
        taskQuery.setSearchText(getView().getTopBarPanel().getSearchField().getText());
        taskQuery.setSortBy(getView().getTopBarPanel().getSortByComboBox().getSelectedItem().toString());
        taskQuery.setSortOrder(getView().getTopBarPanel().getSortOrderComboBox().getSelectedItem().toString());
        performQueryTasks(taskQuery, updateUI);
    }
    
    private void performQueryTasks(TaskQuery taskQuery, boolean updateUI) {
        TaskListManager.getInstance().queryTasks(taskQuery, updateUI);
    }
}
