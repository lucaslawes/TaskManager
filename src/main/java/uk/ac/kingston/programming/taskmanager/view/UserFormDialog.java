/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import uk.ac.kingston.programming.taskmanager.listener.UserFormDialogListener;
import uk.ac.kingston.programming.taskmanager.model.User;

/**
 *
 * @author lucas
 */
public final class UserFormDialog extends JDialog {

    private JLabel nameLabel;
    private JLabel emailAddressLabel;
    private JLabel passwordLabel;
    private JLabel themeLabel;
    private JLabel isAdministratorLabel;
    
    private JTextField nameField;
    private JTextField emailAddressField;
    private JTextField passwordField;
    private JComboBox themeComboBox;
    private JCheckBox isAdministratorCheckBox;
    
    private JButton addEditButton;
    private JButton cancelButton;
    
    private JDialog parent;

    private boolean editMode = false;
    
    private User user = new User();
    
    private UserFormDialogListener userFormDialogListener;
    
    public UserFormDialog(JFrame owner, User user) {
        super(owner);
        parent = this;
        this.user = user;
        editMode = true;
        createChildControls();    
    }
    
    public UserFormDialog(JFrame owner) {
        super(owner);
        parent = this;
        createChildControls();
    }
    
    public void createChildControls() {
        
        ImageIcon image = new ImageIcon(Styling.LOGO_IMAGE);
        setIconImage(image.getImage());
        
        setTitle(editMode ? "Edit User" : "New User");
        
        nameLabel = new JLabel("Name: ");
        nameLabel.setFont(Styling.createFontMedium(true));
        
        nameField = new JTextField(20);
        nameField.setFont(Styling.createFontMedium(false));
        nameField.setEnabled(!editMode);
        
        emailAddressLabel = new JLabel("Email Address: ");
        emailAddressLabel.setFont(Styling.createFontMedium(true));
        
        emailAddressField = new JTextField(20);
        emailAddressField.setFont(Styling.createFontMedium(false));
        
        passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(Styling.createFontMedium(true));
        
        passwordField = new JTextField(20);
        passwordField.setFont(Styling.createFontMedium(false));   

        themeLabel = new JLabel("Theme: ");
        themeLabel.setFont(Styling.createFontMedium(true));
        
        themeComboBox = new JComboBox();
        themeComboBox.setFont(Styling.createFontMedium(false));
        
        DefaultComboBoxModel themeComboBoxModel = new DefaultComboBoxModel();
        
        String[] themes = Styling.THEMES;
        for(String theme : themes) {
            themeComboBoxModel.addElement(theme);    
        }
        
        themeComboBox.setModel(themeComboBoxModel);
        
        isAdministratorLabel = new JLabel("Is Administrator: ");
        isAdministratorLabel.setFont(Styling.createFontMedium(true));
        
        isAdministratorCheckBox = new JCheckBox();
        isAdministratorCheckBox.setFont(Styling.createFontMedium(false));
      
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(Styling.createFontLarge(true));
        cancelButton.setForeground(Styling.createColor(700));
        cancelButton.addActionListener((ActionEvent e) -> {
            if(userFormDialogListener != null) {
                userFormDialogListener.onAddUserCancel();
            }
        });
        
        addEditButton = new JButton(editMode ? "Save User" : "Add User");
        addEditButton.setFont(Styling.createFontLarge(true));
        addEditButton.setForeground(Styling.createColor(700));
        addEditButton.addActionListener((ActionEvent e) -> {
            String name1 = nameField.getText();
            if (name1.equals("")) {
                JOptionPane.showMessageDialog(parent, "Name must be provided", editMode ? "Edit User" : "New User", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String emailAddress = emailAddressField.getText();
            if(emailAddress.equals("")) {
                JOptionPane.showMessageDialog(parent, "Email Address must be provided", editMode ? "Edit User" : "New User", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String password = passwordField.getText();
            if(password.equals("")) {
                JOptionPane.showMessageDialog(parent, "Password must be provided", editMode ? "Edit User" : "New User", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String theme = (String)themeComboBox.getSelectedItem();
            
            boolean isAdministrator = isAdministratorCheckBox.isSelected();
            
            if (!editMode) {
                user = new User(name1, emailAddress, password, isAdministrator);
                user.setTheme(theme);
            } else {
                user.setName(name1);
                user.setEmailAddress(emailAddress);
                user.setPassword(password);
                user.setTheme(theme);
                user.setIsAdministrator(isAdministrator);
            }
            if(userFormDialogListener != null) {
                if(!editMode) {
                    if(userFormDialogListener.userIsUnique(user)) {
                        userFormDialogListener.onAddUser(user);
                    }
                    else {
                        JOptionPane.showMessageDialog(parent, "Name and/or Email Address already exists", editMode ? "Edit User" : "New User", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                else {
                    userFormDialogListener.onEditUser(user);
                }
            }
        });
        
        layoutComponents(); 
        
        if(editMode) {
            nameField.setText(user.getName());
            emailAddressField.setText(user.getEmailAddress());
            passwordField.setText(user.getPassword());
            themeComboBoxModel.setSelectedItem(user.getTheme());
            isAdministratorCheckBox.setSelected(user.getIsAdministrator());
        }
        
    }

    private void layoutComponents() {
        
        setLayout(new BorderLayout());
               
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        addEditButton.setPreferredSize(new Dimension(120, 30));
        cancelButton.setPreferredSize(new Dimension(120, 30));
        
        buttonPanel.add(addEditButton);
        buttonPanel.add(cancelButton);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 5, 0, 20));
        buttonPanel.setPreferredSize(new Dimension(150, 200));
 
        JPanel formPanel = new JPanel();
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        formPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gc = new GridBagConstraints();

        // First Row
        gc.gridy = 0;
        
        gc.weightx = 1;
        gc.weighty = 0.1;
        
        gc.gridx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(30,0,10,5);
        formPanel.add(nameLabel, gc);
        
        gc.gridx = 1;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(30,0,10,0);
        formPanel.add(nameField, gc);
        
        // Second Row
        gc.gridy++;        
        
        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,10,5);
        formPanel.add(emailAddressLabel, gc);
        
        gc.gridy = 1;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,0,10,0);
        formPanel.add(emailAddressField, gc);
        
        // Third Row
        gc.gridy++;        
        
        gc.weightx = 1;
        gc.weighty = 0.2;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0,0,10,5);
        formPanel.add(passwordLabel, gc);
        
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(0,0,10,0);
        formPanel.add(passwordField, gc);                
        
        // Fourth Row
        gc.gridy++;        
        
        gc.weightx = 1;
        gc.weighty = 0.2;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0,0,10,5);
        formPanel.add(themeLabel, gc);
        
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(0,0,10,0);
        formPanel.add(themeComboBox, gc); 
        
        // Fifth Row
        gc.gridy++;        
        
        gc.weightx = 1;
        gc.weighty = 0.2;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0,0,10,5);
        formPanel.add(isAdministratorLabel, gc);
        
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(0,0,10,0);
        formPanel.add(isAdministratorCheckBox, gc); 
       
        add(formPanel, BorderLayout.CENTER);
        
        add(buttonPanel, BorderLayout.EAST);
        
        
    }
    
    public void setUserFormDialogListener(UserFormDialogListener userFormDialogListener) {
        this.userFormDialogListener = userFormDialogListener;
    }    
}
