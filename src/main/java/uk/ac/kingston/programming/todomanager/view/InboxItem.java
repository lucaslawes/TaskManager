/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import uk.ac.kingston.programming.todomanager.model.Task;

/**
 *
 * @author lucas
 */
public final class InboxItem extends JLabel {
    
    private final Task task;
    private boolean active = false;
    
    public InboxItem(Task task) {
        this.task = task;
        setOpaque(true);

        setText(task.toHtml(task.isCompleted()));
        setFont(Styling.createFontMedium(false));    
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, false), 
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        
        switch(task.getPriority()) {
            case 1:
                setForeground(new Color(76,175,80));
                break;
            case 2:
                setForeground(new Color(255,152,0));
                break;                
            case 3:
                setForeground(Color.BLACK);
                break;        
        }  
        
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                if(!isActive()){
                    setBackground(Styling.createColor(100));
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
            }                
        });
        
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
                if(!isActive()){
                    setBackground(Color.WHITE);
                }
            }
        });        
    }

    /**
     * @return the task
     */
    public Task getTask() {
        return task;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        if(this.active) {
            setBackground(Styling.createColor(200));
        }
        else {
            setBackground(Color.WHITE);
        }
    }
}
