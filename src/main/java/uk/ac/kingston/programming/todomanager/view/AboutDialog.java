/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.view;

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
import uk.ac.kingston.programming.todomanager.model.Settings;

/**
 *
 * @author k1746516
 */
public final class AboutDialog extends JDialog {

    private ActionListener onOkClicked;
    
    private Settings settings;
    
    private static AboutDialog instance;
    
    public static AboutDialog getInstance() {
        if(instance == null) instance = new AboutDialog();
        return instance;
    }
    
    private AboutDialog() {

    }

    public void createChildControls() {
        
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
        
        JLabel textLabel = new JLabel("<html><body><h2>" + getSettings().getTitle() + " v" + getSettings().getVersion() + "</h2>Created by "+"\n"+ getSettings().getAuthors() + "</body></html>");
        textLabel.setFont(Styling.createFontLarge(false));
        textPanel.add(textLabel);
        
        add(textPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,20,40,20));
        
        JButton okButton = new JButton("OK");
        okButton.setFont(Styling.createFontXLarge(true));
        okButton.setForeground(Styling.createColor(700));
        okButton.addActionListener((ActionEvent e) -> {
            if(onOkClicked != null) {
                onOkClicked.actionPerformed(e);
            }
        });
        
        buttonPanel.add(okButton);
        
        add(buttonPanel, BorderLayout.SOUTH);         
    }
    
    public void setOnOkClicked(ActionListener onOkClicked) {
        this.onOkClicked = onOkClicked;
    }

    /**
     * @return the settings
     */
    private Settings getSettings() {
        return settings;
    }

    /**
     * @param settings the settings to set
     */
    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
