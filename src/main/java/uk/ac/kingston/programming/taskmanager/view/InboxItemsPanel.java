/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import uk.ac.kingston.programming.taskmanager.model.Task;
import uk.ac.kingston.programming.taskmanager.model.TaskList;
import uk.ac.kingston.programming.taskmanager.model.User;

/**
 *
 * @author lucas
 */
public final class InboxItemsPanel extends AppPanel{
    private JPopupMenu popup;
    private Task currentTask;
    private InboxItem currentInboxItem;
    private ArrayList<InboxItem> inboxItems = new ArrayList<>();
    
    public InboxItemsPanel() {
        super(false, true);

        createChildControls();
    }
    
    @Override
    public void createChildControls() {
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));        
    }
    
    @Override
    public void onTaskListProviderSet() {
        getTaskListProvider().addTaskListListener(this);
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
    public void onTaskUpdated(Task task) {
        refresh();
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
    public void onTaskDeleted(Task task) {
        refresh();
    }
    
    private void refresh() {
        
        TaskList taskList = getTaskListProvider().getTaskList();
        removeAll(); 
        
        currentInboxItem = null;
        inboxItems = new ArrayList<>();
        
        popup = new JPopupMenu();
               
        JMenuItem editItem = new JMenuItem("Edit Task");
        editItem.setFont(new Font("Segoe UI", 0, 16));
        popup.add(editItem);
        
        editItem.addActionListener((ActionEvent e) -> {
            if(currentInboxItem == null) {
                return;
            }
            Task task = currentInboxItem.getTask();
            getTaskListProvider().editTask(task);
        });         

        JMenuItem removeItem = new JMenuItem("Delete Task");
        removeItem.setFont(new Font("Segoe UI", 0, 16));
        popup.add(removeItem);
        
        removeItem.addActionListener((ActionEvent e) -> {
            if(currentInboxItem == null) {
                return;
            }
            Task task = currentInboxItem.getTask();
            if(JOptionPane.showConfirmDialog(null, "Do you want to delete task '" + task.getTitle() + "'?", "Delete Task", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                return;
            }
            getTaskListProvider().deleteTask(task);
        });
        
        for(int i = 0; i < taskList.getTasks().size(); i++){
            Task task = taskList.getTasks().get(i);
            if(task.isSelected()) {               
                InboxItem inboxItem = new InboxItem(task);
                if(task.equals(currentTask)){
                    inboxItem.setActive(true);
                }
                inboxItems.add(inboxItem);
                inboxItem.setPreferredSize(new Dimension(200, 100));
                inboxItem.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        currentInboxItem = (InboxItem)e.getComponent();
                        currentTask = currentInboxItem.getTask();
                        for(InboxItem inboxItem : inboxItems) {
                            inboxItem.setActive(inboxItem.equals(currentInboxItem));
                        }
                        if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                            getTaskListProvider().selectTask(currentInboxItem.getTask());                            
                            getTaskListProvider().editTask(task);
                        }
                        else if(e.getButton() == MouseEvent.BUTTON1) {
                            getTaskListProvider().selectTask(currentInboxItem.getTask());                            
                        }
                        else if(e.getButton() == MouseEvent.BUTTON3) {
                            popup.show(currentInboxItem, e.getX(), e.getY());
                        }
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
                add(inboxItem);
            }
        }
        int numberOfTasks = taskList.getTasks().size();
        String statusBarText = inboxItems.size() + " of " + numberOfTasks;
        getTaskListProvider().setStatusBarText(numberOfTasks == 1 ? statusBarText + " task showing" : statusBarText + " tasks showing");
        updateUI();
    }
}
