/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import uk.ac.kingston.programming.taskmanager.controller.NavBarLinkListener;

/**
 *
 * @author lucas
 */
public final class NavBarLinksPanel extends JPanel{
    
    private NavBarLinkHeader navBarLinkHeader;
    private ArrayList<NavBarLink> navBarLinks;
    
    private NavBarLinkListener navbarLinkListener;
    
    public NavBarLinksPanel(String title, String[] links) {
        
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        navBarLinkHeader = new NavBarLinkHeader(title);
        add(navBarLinkHeader);    
        
        navBarLinks = new ArrayList<>();
        
        for(String link : links) {
            NavBarLink navBarLink = new NavBarLink("   " + link);
            navBarLink.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    navBarLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                }                
            });
            navBarLink.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(navbarLinkListener != null) {
                        navbarLinkListener.onNavbarLinkClicked(title, link);
                    }
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
                }
            });
            navBarLinks.add(navBarLink);
            add(navBarLink);
        }
        
        setMaximumSize(new Dimension(200, 25*links.length+60));
        setPreferredSize(new Dimension(200, 25*links.length+60));
        setSize(new Dimension(200, 25*links.length+60));                
    }

    /**
     * @param navbarLinkListener the navbarLinkListener to set
     */
    public void setNavbarLinkListener(NavBarLinkListener navbarLinkListener) {
        this.navbarLinkListener = navbarLinkListener;
    }
}
