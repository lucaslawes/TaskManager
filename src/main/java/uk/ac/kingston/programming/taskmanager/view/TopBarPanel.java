/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import uk.ac.kingston.programming.taskmanager.controller.TopBarListener;
import uk.ac.kingston.programming.taskmanager.model.User;

/**
 *
 * @author lucas
 */
public final class TopBarPanel extends AppPanel{
    
    private JLabel logoLabel;
    private JLabel titleLabel;
    private JLabel userLabel;
    private JLabel logoutLabel;

    private JLabel searchLabel;
    private JTextField searchField;
    
    private JLabel sortByLabel;
    private JComboBox sortByComboBox;
    private JComboBox sortOrderComboBox;   
    
    private JButton newButton;
    
    private TopBarListener topBarListener;
    
    public TopBarPanel() {
        
        super(false, true);
        
        createChildControls();
    } 
    
    @Override
    public void createChildControls() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Styling.createColor(900));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        logoLabel = new JLabel(new ImageIcon(Styling.LOGO_IMAGE));
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        
        headerPanel.add(logoLabel, BorderLayout.WEST);
        
        titleLabel = new JLabel("Task Manager");
        titleLabel.setFont(Styling.createFontXXXLarge(true));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        
        headerPanel.add(titleLabel);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setOpaque(false);
        userPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 0, 0));
        
        userLabel = new JLabel();
        userLabel.setFont(Styling.createFontLarge(false));
        userLabel.setForeground(Color.WHITE);        
        userLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));
        
        userPanel.add(userLabel);
        
        logoutLabel = new JLabel();
        logoutLabel.setFont(Styling.createFontXLarge(true));
        logoutLabel.setForeground(Color.WHITE);        
        logoutLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));
        logoutLabel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseDragged(MouseEvent e) {
            }                
        });
        logoutLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(topBarListener != null) {
                    topBarListener.onLogoutClicked();
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
        
        userPanel.add(logoutLabel);
        
        headerPanel.add(userPanel, BorderLayout.EAST);
        
        add(headerPanel);
        
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.setOpaque(false);
        
        JLabel paddingLabel = new JLabel(" ");
        paddingLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        actionPanel.add(paddingLabel);
        
        newButton = new JButton("Add Task");
        newButton.setFont(Styling.createFontXLarge(true));
        newButton.setForeground(Styling.createColor(700));       
        newButton.addActionListener((ActionEvent e) -> {
            getTaskListProvider().addTask();
        });
        actionPanel.add(newButton);
        
        searchLabel = new JLabel("Search: ");
        searchLabel.setFont(Styling.createFontXLarge(true));
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setBorder(BorderFactory.createEmptyBorder(0, 17, 0, 0));
        actionPanel.add(searchLabel);
        
        searchField = new JTextField(20);
        searchField.setFont(Styling.createFontXLarge(false));
        JTextFieldChangeListener.addChangeListener(searchField, e -> {
            getTaskListProvider().searchTaskList(searchField.getText());
        });        
        actionPanel.add(searchField);
               
        sortByLabel = new JLabel("Sort: ");
        sortByLabel.setFont(Styling.createFontXLarge(true));
        sortByLabel.setForeground(Color.WHITE);
        sortByLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        actionPanel.add(sortByLabel);
        
        sortByComboBox = new JComboBox();
        sortByComboBox.setFont(Styling.createFontXLarge(false));

        DefaultComboBoxModel sortByComboBoxModel = new DefaultComboBoxModel();
        sortByComboBoxModel.addElement("Title");
        sortByComboBoxModel.addElement("Assignee");
        sortByComboBoxModel.addElement("Priority");        
        sortByComboBoxModel.addElement("Due Date");        
        sortByComboBox.setModel(sortByComboBoxModel);
        sortByComboBox.setSelectedIndex(3);
        sortByComboBox.addActionListener((ActionEvent e) -> {
            getTaskListProvider().sortTasks(sortByComboBox.getSelectedItem().toString(), sortOrderComboBox.getSelectedItem().toString());
        });
        
        actionPanel.add(sortByComboBox);
        
        sortOrderComboBox = new JComboBox();
        sortOrderComboBox.setFont(Styling.createFontXLarge(false));
        
        DefaultComboBoxModel sortOrderComboBoxModel = new DefaultComboBoxModel();
        sortOrderComboBoxModel.addElement("Ascending");
        sortOrderComboBoxModel.addElement("Descending");
        sortOrderComboBox.setModel(sortOrderComboBoxModel);
        sortOrderComboBox.setSelectedIndex(1);
        sortOrderComboBox.addActionListener((ActionEvent e) -> {
            getTaskListProvider().sortTasks(sortByComboBox.getSelectedItem().toString(), sortOrderComboBox.getSelectedItem().toString());
        });        
        actionPanel.add(sortOrderComboBox);
        
        add(actionPanel);
        
    }
    
    @Override
    public void onTaskListProviderSet() {
        getTaskListProvider().addTaskListListener(this);
        searchField.setText(getTaskListProvider().getTaskQuery().getSearchText());
        sortByComboBox.setSelectedItem(getTaskListProvider().getTaskQuery().getSortBy());
        sortOrderComboBox.setSelectedItem(getTaskListProvider().getTaskQuery().getSortOrder());
        if(getTaskListProvider().getUser().getName().length() > 0) {
            onLogin(getTaskListProvider().getUser());
        }
    }     

    @Override
    public void onLogin(User user) {
        userLabel.setText(user.getName() + "(" + user.getEmailAddress() + ")");
        logoutLabel.setText("Logout");
        updateUI();
    }

    @Override
    public void onLogout() {
        userLabel.setText("");
        logoutLabel.setText("");
        updateUI();
    }

    /**
     * @param topBarListener the topBarListener to set
     */
    public void setTopBarListener(TopBarListener topBarListener) {
        this.topBarListener = topBarListener;
    }
}
