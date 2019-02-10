/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import uk.ac.kingston.programming.taskmanager.model.Settings;

/**
 *
 * @author k1746516
 */
public final class AboutDialog extends JDialog {

    private ActionListener onOkClicked;
    
    public AboutDialog(Settings settings) {
        
        setLayout(new BorderLayout());
        
        ImageIcon image = new ImageIcon(Styling.LOGO_IMAGE);
        setIconImage(image.getImage());
        
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        iconPanel.setBorder(BorderFactory.createEmptyBorder(40,20,40,20));
        
        JLabel iconLabel = new JLabel(new ImageIcon(Styling.LOGO_IMAGE));
        
        iconPanel.add(iconLabel);
        
        add(iconPanel, BorderLayout.WEST);
        
        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));  
        textPanel.setBorder(BorderFactory.createEmptyBorder(30,10,40,40));
        
        JLabel textLabel = new JLabel("<html><body><h2>" + settings.getTitle() + " v" + settings.getVersion() + "</h2>Created by "+"\n"+ settings.getAuthors() + "</body></html>");
        textLabel.setFont(Styling.createFontLarge(false));
        textPanel.add(textLabel);
        
        add(textPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,20,40,20));
        
        JButton okButton = new JButton("OK");
        okButton.setFont(Styling.createFontXLarge(true));
        okButton.setForeground(Styling.createColor(700));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(onOkClicked != null) {
                    onOkClicked.actionPerformed(e);
                }
            }
        });
    buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
    }
    
    public void setOnOkClicked(ActionListener onOkClicked) {
        this.onOkClicked = onOkClicked;
    }
}
