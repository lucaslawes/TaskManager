/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import uk.ac.kingston.programming.todomanager.listener.PreferencesDialogListener;
import uk.ac.kingston.programming.todomanager.model.User;

/**
 *
 * @author lucas
 */
public final class PreferencesDialog extends JDialog {
    
    private JLabel themeLabel;
    private JComboBox themeComboBox;
    private JButton saveButton;
    private JButton cancelButton;

    private User user;
    private String originalTheme;
    
    private PreferencesDialogListener preferencesDialogListener;
    
    private static PreferencesDialog instance;
    
    public static PreferencesDialog getInstance() {
        if(instance == null) instance = new PreferencesDialog();
        return instance;
    }
    private PreferencesDialog() {
        
        setTitle("Edit Preferences"); 
    }

    public void createChildControls() {
        setLayout(new BorderLayout());
    
        ImageIcon image = new ImageIcon(Styling.LOGO_IMAGE);
        setIconImage(image.getImage());

        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        themeLabel = new JLabel("Theme: ");
        themeLabel.setFont(Styling.createFontMedium(true));
        
        optionsPanel.add(themeLabel);
        
        themeComboBox = new JComboBox();
        themeComboBox.setFont(Styling.createFontMedium(false));
        
        DefaultComboBoxModel themeComboBoxModel = new DefaultComboBoxModel();
        
        String[] themes = Styling.THEMES;
        for(String theme : themes) {
            themeComboBoxModel.addElement(theme);    
        }
        
        themeComboBoxModel.setSelectedItem(getUser().getTheme());
        themeComboBox.setModel(themeComboBoxModel);
        themeComboBox.addActionListener((ActionEvent e) -> {
            if(preferencesDialogListener != null) {
                preferencesDialogListener.themeChanged((String)themeComboBox.getSelectedItem());
            }
        });        
        
        optionsPanel.add(themeComboBox);
        
        add(optionsPanel, BorderLayout.CENTER);
        
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        saveButton = new JButton("Save");
        saveButton.setFont(Styling.createFontXLarge(true));
        saveButton.setForeground(Styling.createColor(700));
        saveButton.addActionListener((ActionEvent e) -> {
            if(preferencesDialogListener != null) {
                preferencesDialogListener.saveClicked((String)themeComboBox.getSelectedItem());
            }            
        });

        buttonsPanel.add(saveButton);
        
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(Styling.createFontXLarge(true));
        cancelButton.setForeground(Styling.createColor(700));
        cancelButton.addActionListener((ActionEvent e) -> {
            if (preferencesDialogListener != null) {
                preferencesDialogListener.cancelClicked(originalTheme);
            }
        });
        
        buttonsPanel.add(cancelButton);
        
        add(buttonsPanel, BorderLayout.EAST);        
    }

    /**
     * @return the user
     */
    private User getUser() {
        return user;
    }
    
    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
        originalTheme = user.getTheme();
    }
    
    /**
     * @param preferencesDialogListener the preferencesDialogListener to set
     */
    public void setPreferencesDialogListener(PreferencesDialogListener preferencesDialogListener) {
        this.preferencesDialogListener = preferencesDialogListener;
    }
}
