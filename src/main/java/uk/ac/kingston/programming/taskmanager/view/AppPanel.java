/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.view;

import javax.swing.JPanel;

/**
 *
 * @author lucas
 */
public abstract class AppPanel extends JPanel {
    
    public AppPanel(boolean isDoubleBuffered, boolean opaque) {
        super(isDoubleBuffered);
        setOpaque(opaque);
    }
    
    public void redraw() {
        removeAll();
        createChildControls();
        updateUI();
    }
    
    public abstract void createChildControls();
}
