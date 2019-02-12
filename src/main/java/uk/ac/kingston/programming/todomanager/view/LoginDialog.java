/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import uk.ac.kingston.programming.todomanager.listener.LoginDialogListener;

/**
 *
 * @author lucas
 */
public final class LoginDialog extends JDialog {
    
    private JButton loginButton;
    private JButton cancelButton;
    private JTextField emailAddressField;
    private JPasswordField passwordField;
    
    private LoginDialogListener loginDialogListener;
    
    private static LoginDialog instance = null;
    
    public static LoginDialog getInstance(){
        if(instance == null) instance = new LoginDialog();
        return instance;
    }
    
    private LoginDialog() {
        setTitle("Login");
        
        createChildControls();
    }

    private void createChildControls() {
        
        ImageIcon image = new ImageIcon(Styling.LOGO_IMAGE);
        setIconImage(image.getImage());
        
        setLayout(new BorderLayout());
        
        loginButton = new JButton("OK");
        loginButton.setFont(Styling.createFontXLarge(true));
        loginButton.setForeground(Styling.createColor(700));
        
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(Styling.createFontXLarge(true));
        cancelButton.setForeground(Styling.createColor(700));
        
        emailAddressField = new JTextField(20);
        emailAddressField.setFont(Styling.createFontLarge(false));

        passwordField = new JPasswordField(20);
        passwordField.setFont(Styling.createFontLarge(false));
               
        loginButton.addActionListener((ActionEvent e) -> {
            String emailAddress = emailAddressField.getText();
            String password = String.valueOf(passwordField.getPassword());
            
            if(loginDialogListener != null) {
                loginDialogListener.onLoginButtonPressed(emailAddress, password);
            }
        });

        cancelButton.addActionListener((ActionEvent e) -> {
            if(loginDialogListener != null) {
                loginDialogListener.onCancelButtonPressed();
            }
        });       
        
        layoutControls();
                
        setSize(450,250);
        setMinimumSize(new Dimension(450,250)); 
        
    }
    
    private void layoutControls() {
        
        JPanel controlsPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        
        Border spaceBorder = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        
        controlsPanel.setBorder(spaceBorder);
        buttonsPanel.setBorder(spaceBorder);
        
        controlsPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gc = new GridBagConstraints();
        
        Insets rightPadding = new Insets(0, 0, 0, 5);
        Insets noPadding = new Insets(0, 0, 0, 0);
        
        gc.gridy = 0;

        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;
        
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setFont(Styling.createFontLarge(true));
        controlsPanel.add(emailLabel, gc);
        
        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        controlsPanel.add(emailAddressField, gc);        

        gc.gridy++;
        
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;
        
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(Styling.createFontLarge(true));
        controlsPanel.add(passwordLabel, gc);
        
        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        controlsPanel.add(passwordField, gc);
             
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(loginButton);
        buttonsPanel.add(cancelButton);

        Dimension buttonSize = cancelButton.getPreferredSize();
        loginButton.setPreferredSize(buttonSize);
        
        setLayout(new BorderLayout());
        
        ImageIcon imageIcon = new ImageIcon(Styling.LOGO_IMAGE);
        JPanel imagePanel = new JPanel();
        imagePanel.setBorder(spaceBorder);
        imagePanel.add(new JLabel(imageIcon));
        add(imagePanel, BorderLayout.WEST);
        
        JLabel titleLabel = new JLabel("Welcome to Task Manager, please login:-");
        titleLabel.setFont(Styling.createFontLarge(false));
       
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBorder(spaceBorder);
        titlePanel.add(titleLabel, BorderLayout.WEST);
        
        add(titlePanel, BorderLayout.NORTH);
        
        add(controlsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

    }
    
    public void setFocus() {
        emailAddressField.requestFocus();
    }
    
    public void prepare() {
        loginButton.setForeground(Styling.createColor(700));
        cancelButton.setForeground(Styling.createColor(700));
    }
    
    public void setLoginDialogListener(LoginDialogListener loginDialogListener) {
        this.loginDialogListener = loginDialogListener;
    }
}
