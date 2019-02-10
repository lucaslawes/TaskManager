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
import javax.swing.JLabel;

/**
 *
 * @author lucas
 */
public final class NavBarLink extends JLabel{
    
    public NavBarLink(String link) {

        setOpaque(false);
        
        setText(link);
        setFont(Styling.createFontXLarge(false));
        setForeground(Color.WHITE);       

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                setForeground(Styling.createColor(100));
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
}
