/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.view;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 *
 * @author lucas
 */
public final class StatusBarPanel extends AppPanel{
    
    private JLabel statusTextLabel;
    
    public StatusBarPanel() {
        super(true, true);
        
        createChildControls();
    }

    @Override
    public void createChildControls() {
        
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(Styling.createColor(800));
        
        statusTextLabel = new JLabel();
        statusTextLabel.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 30));
        statusTextLabel.setFont(Styling.createFontMedium(false));
        statusTextLabel.setForeground(Color.white);
        
        add(statusTextLabel);        
    }
    
    public void setStatusBarText(String text) {
        statusTextLabel.setText(text);
        updateUI();
    }
}
