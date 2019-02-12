/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import uk.ac.kingston.programming.todomanager.listener.PreviewPanelListener;
import uk.ac.kingston.programming.todomanager.model.Task;

/**
 *
 * @author lucas
 */
public final class PreviewPanel extends AppPanel{

    private PreviewPanelListener previewPanelListener;
    
    public PreviewPanel() {
        super(true, true);
        
        createChildControls();
    }
    
    @Override
    public void createChildControls() {
        setBackground(Styling.createColor(100));
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));        
    }
    
    public void refresh() {
        refresh(null);
    }
    
    public void refresh(Task task) {
        
        removeAll();

        if(task != null) {
       
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            PreviewPanelTaskItem previewPanelTaskItem = new PreviewPanelTaskItem(task);
            previewPanelTaskItem.setPreferredSize(new Dimension(300, 400));
            previewPanelTaskItem.setPreviewPanelTaskItemListener((Task task1) -> {
                if(previewPanelListener != null) {
                    previewPanelListener.onTaskItemUpdated(task1);
                }
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
                if(previewPanelListener != null) {
                    previewPanelListener.onDeleteButtonClicked(task);
                }
            });

            buttonPanel.add(deleteButton);

            JButton editButton = new JButton("Edit");
            editButton.setFont(Styling.createFontXLarge(true));
            editButton.setForeground(Styling.createColor(700));            
            editButton.addActionListener((ActionEvent e) -> {
                if(previewPanelListener != null) {
                    previewPanelListener.onEditButtonClicked(task);
                }
            });

            buttonPanel.add(editButton);

            add(buttonPanel);
        }
        
        updateUI();
    }    

    /**
     * @param previewPanelListener the previewPanelListener to set
     */
    public void setPreviewPanelListener(PreviewPanelListener previewPanelListener) {
        this.previewPanelListener = previewPanelListener;
    }
}
