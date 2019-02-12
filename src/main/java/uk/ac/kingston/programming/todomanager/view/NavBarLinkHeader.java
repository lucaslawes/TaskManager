/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.view;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 *
 * @author lucas
 */
public final class NavBarLinkHeader extends JLabel{
    
    public NavBarLinkHeader(String link) {
    
        setOpaque(false);
      
        setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        setText(link);
        setFont(Styling.createFontXXLarge(true));
        setForeground(Color.WHITE);       
    }
}
