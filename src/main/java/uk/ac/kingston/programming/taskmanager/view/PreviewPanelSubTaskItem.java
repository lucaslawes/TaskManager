/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.view;

import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import uk.ac.kingston.programming.taskmanager.model.SubTask;

/**
 *
 * @author lucas
 */
public final class PreviewPanelSubTaskItem extends JCheckBox {
    
    private SubTask subTask;
    
    public PreviewPanelSubTaskItem(SubTask subTask) {
        
        setFont(Styling.createFontMedium(false));
        setText(subTask.toHtml(subTask.isCompleted(), true));
        
        setSelected(subTask.isCompleted());
        addActionListener((ActionEvent e) -> {
            subTask.setCompleted(isSelected());
            setText(subTask.toHtml(isSelected(), true));
        });
        this.subTask = subTask;
        
        setOpaque(false);
    }

    /**
     * @return the subTask
     */
    public SubTask getSubTask() {
        return subTask;
    }
}
