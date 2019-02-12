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
    
    private final SubTask subTask;
    
    public PreviewPanelSubTaskItem(SubTask subTask) {
        
        this.subTask = subTask;
        
        setFont(Styling.createFontMedium(false));
        setText(subTask.toHtml(subTask.isCompleted(), true));
        
        setSelected(subTask.isCompleted());
        addActionListener((ActionEvent e) -> {
            subTask.setCompleted(isSelected());
            setText(subTask.toHtml(isSelected(), true));
        });
        
        setOpaque(false);
    }

    /**
     * @return the subTask
     */
    public SubTask getSubTask() {
        return subTask;
    }
}
