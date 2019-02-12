/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 *
 * @author lucas
 */
public final class NavBarLink extends JLabel{
    
    private String title = "";
    private String link = "";
    
    private boolean selected = false;
    
    public NavBarLink(String title, String link) {

        this.title = title;
        this.link = link;
        
        setOpaque(false);
        
        setText(link);
        setFont(Styling.createFontXLarge(false));
        setForeground(Color.WHITE);       
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                if(!isSelected()) {
                    setForeground(Styling.createColor(100));
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
                setForeground(Color.WHITE);

            }
        });          
    }

    void setSelected(boolean selected) {
        this.selected = selected;
        if(selected) {
            setFont(Styling.createFontXLarge(true));
        }
        else {
            setFont(Styling.createFontXLarge(false));
        }
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }
}
