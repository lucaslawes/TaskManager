/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import uk.ac.kingston.programming.taskmanager.listener.PreviewPanelTaskItemListener;
import uk.ac.kingston.programming.taskmanager.model.SubTask;
import uk.ac.kingston.programming.taskmanager.model.Task;

/**
 *
 * @author lucas
 */
public final class PreviewPanelTaskItem extends JPanel {
    
    private PreviewPanelTaskItemListener previewPanelTaskItemListener;
    
    public PreviewPanelTaskItem(Task task) {
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        
        JCheckBox taskCheckBox = new JCheckBox(task.toHtml(task.isCompleted()));
        taskCheckBox.setFont(Styling.createFontLarge(false));
        switch(task.getPriority()) {
            case 1:
                taskCheckBox.setForeground(new Color(76,175,80));
                break;
            case 2:
                taskCheckBox.setForeground(new Color(255,152,0));
                break;                
            case 3:
                taskCheckBox.setForeground(Color.BLACK);
                break;        
        } 
        taskCheckBox.setSelected(task.isCompleted());
        taskCheckBox.addActionListener((ActionEvent e) -> {
            task.setCompleted(taskCheckBox.isSelected());
            taskCheckBox.setText(task.toHtml(task.isCompleted()));
            if(previewPanelTaskItemListener != null) {
                previewPanelTaskItemListener.onTaskClicked(task);
            }
        });
        taskCheckBox.setOpaque(false);
        
        add(taskCheckBox);
        
        for(SubTask subTask : task.getSubTasks()) {
            PreviewPanelSubTaskItem previewPanelSubTaskItem = new PreviewPanelSubTaskItem(subTask);         
            previewPanelSubTaskItem.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
            add(previewPanelSubTaskItem);
        }
    }

    /**
     * @param previewPanelTaskItemListener the previewPanelTaskItemListener to set
     */
    public void setPreviewPanelTaskItemListener(PreviewPanelTaskItemListener previewPanelTaskItemListener) {
        this.previewPanelTaskItemListener = previewPanelTaskItemListener;
    }
}
