/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import uk.ac.kingston.programming.todomanager.listener.InboxItemsPanelListener;
import uk.ac.kingston.programming.todomanager.model.Task;
import uk.ac.kingston.programming.todomanager.model.TaskList;

/**
 *
 * @author lucas
 */
public final class InboxItemsPanel extends AppPanel{
    private JPopupMenu popup;
    private Task currentTask;
    private InboxItem currentInboxItem;
    private ArrayList<InboxItem> inboxItems = new ArrayList<>();
    
    private InboxItemsPanelListener inboxItemsPanelListener;
    
    public InboxItemsPanel() {
        super(false, true);

        createChildControls();
    }
    
    @Override
    public void createChildControls() {
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));        
    }
       
    public void refresh(TaskList taskList) {
        
        removeAll(); 
        
        currentInboxItem = null;
        inboxItems = new ArrayList<>();
        
        popup = new JPopupMenu();
               
        JMenuItem editItem = new JMenuItem("Edit Task");
        editItem.setFont(Styling.createFontLarge(false));
        popup.add(editItem);
        
        editItem.addActionListener((ActionEvent e) -> {
            if(currentInboxItem == null) {
                return;
            }
            Task task = currentInboxItem.getTask();
            if(inboxItemsPanelListener != null) {
                inboxItemsPanelListener.onTaskEditClicked(task);
            }
        });         

        JMenuItem removeItem = new JMenuItem("Delete Task");
        removeItem.setFont(Styling.createFontLarge(false));
        popup.add(removeItem);
        
        removeItem.addActionListener((ActionEvent e) -> {
            if(currentInboxItem == null) {
                return;
            }
            Task task = currentInboxItem.getTask();
            if(inboxItemsPanelListener != null) {
                inboxItemsPanelListener.onTaskDeleteClicked(task);
            }
        });
        
        for(int i = 0; i < taskList.getTasks().size(); i++){
            Task task = taskList.getTasks().get(i);
            if(task.isSelected()) {               
                InboxItem inboxItem = new InboxItem(task);
                if(task.equals(currentTask)){
                    inboxItem.setActive(true);
                }
                inboxItems.add(inboxItem);
                inboxItem.setPreferredSize(new Dimension(200, 75));
                inboxItem.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        currentInboxItem = (InboxItem)e.getComponent();
                        currentTask = currentInboxItem.getTask();
                        inboxItems.forEach((inboxItem) -> {
                            inboxItem.setActive(inboxItem.equals(currentInboxItem));
                        });
                        if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                            if(inboxItemsPanelListener != null) {
                                inboxItemsPanelListener.onTaskEditClicked(currentTask);
                            }                            
                        }
                        else if(e.getButton() == MouseEvent.BUTTON1) {
                            if(inboxItemsPanelListener != null) {
                                inboxItemsPanelListener.onTaskSelectClicked(currentTask);
                            }                               
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
        
        if(inboxItemsPanelListener != null) {
            int numberOfTasks = taskList.getTasks().size();
            String statusBarText = inboxItems.size() + " of " + numberOfTasks;
            inboxItemsPanelListener.onSetStatusBarText(numberOfTasks == 1 ? statusBarText + " task showing" : statusBarText + " tasks showing");
        }
        
        updateUI();
    }

    public void setActive(Task activeTask) {
        inboxItems.forEach((inboxItem) -> {
            Task task = inboxItem.getTask();
            inboxItem.setActive(task.equals(activeTask));
            if(inboxItem.isActive()) {
                currentTask = task;
                currentInboxItem = inboxItem;
            }
        });
    }
    /**
     * @param inboxItemsPanelListener the inboxItemsPanelListener to set
     */
    public void setInboxItemsPanelListener(InboxItemsPanelListener inboxItemsPanelListener) {
        this.inboxItemsPanelListener = inboxItemsPanelListener;
    }
}
