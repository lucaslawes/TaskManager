/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.view;

import uk.ac.kingston.programming.taskmanager.controller.LoginListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author lucas
 */
public final class LoginDialog extends JDialog {
    
    private JButton loginButton;
    private JButton cancelButton;
    private JTextField emailAddressField;
    private JPasswordField passwordField;
    
    private LoginListener loginListener;
    
    public LoginDialog() {
        
        setTitle("Login");
        
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
        
        layoutControls();
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailAddress = emailAddressField.getText();
                String password = String.valueOf(passwordField.getPassword());
                
                if(loginListener != null) {
                    loginListener.onLoginButtonPressed(emailAddress, password);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(loginListener != null) {
                    loginListener.onCancelButtonPressed();
                }
            }
        });       
        
        setSize(450,250);
        setMinimumSize(new Dimension(450,250));
        setVisible(true);    
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
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
}
