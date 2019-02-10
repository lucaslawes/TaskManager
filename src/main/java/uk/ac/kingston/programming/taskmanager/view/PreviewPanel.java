/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import uk.ac.kingston.programming.taskmanager.model.Task;
import uk.ac.kingston.programming.taskmanager.model.User;

/**
 *
 * @author lucas
 */
public final class PreviewPanel extends AppPanel{
    
   
    public PreviewPanel() {
        super(true, true);
        
        createChildControls();
    }   
    
    @Override
    public void createChildControls() {
        setBackground(Styling.createColor(100));
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
       
    }
    
    @Override
    public void onTaskListProviderSet() {
        getTaskListProvider().addTaskListListener(this);
    }  
    
    @Override
    public void onLogin(User user) {
        refresh(null);
    }

    @Override
    public void onLogout() {
        refresh(null);
    }
    
    @Override
    public void onTasksUpdated() {
        refresh(null);
    }

    @Override
    public void onTaskAdded(Task task) {
        refresh(null);
    }
    
    @Override
    public void onTaskSelected(Task task) {
        refresh(task);

    }

    @Override
    public void onTaskDeleted(Task task) {
        refresh(null);
    }

    @Override
    public void onTaskUpdated(Task task) {
        refresh(task);
    }    

    private void refresh(Task task) {
        
        removeAll();

        if(task != null) {
       
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            PreviewPanelTaskItem previewPanelTaskItem = new PreviewPanelTaskItem(task);
            previewPanelTaskItem.setPreferredSize(new Dimension(300, 100));
            previewPanelTaskItem.setPreviewPanelTaskItemListener((Task task1) -> {
                getTaskListProvider().fireOnTaskUpdated(task1);
            });
            
            JScrollPane scrollPane = new JScrollPane(previewPanelTaskItem, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.getVerticalScrollBar().setUnitIncrement(15);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.getViewport().setBackground(getBackground());
            add(scrollPane);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setOpaque(false);
                    
            JButton deleteButton = new JButton("Delete");
            deleteButton.setFont(Styling.createFontXLarge(true));
            deleteButton.setForeground(Styling.createColor(700));                   
            deleteButton.addActionListener((ActionEvent e) -> {
                if(JOptionPane.showConfirmDialog(getParent(), "Do you want to delete task '" + task.getTitle() + "'?", "Delete Task", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                    return;
                }
                getTaskListProvider().deleteTask(task);
            });

            buttonPanel.add(deleteButton);

            JButton editButton = new JButton("Edit");
            editButton.setFont(Styling.createFontXLarge(true));
            editButton.setForeground(Styling.createColor(700));            
            editButton.addActionListener((ActionEvent e) -> {
                getTaskListProvider().editTask(task);
            });

            buttonPanel.add(editButton);

            add(buttonPanel);
        }
        updateUI();
    }
}
