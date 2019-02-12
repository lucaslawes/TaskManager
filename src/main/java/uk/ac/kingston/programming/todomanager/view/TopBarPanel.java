/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
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
import uk.ac.kingston.programming.todomanager.model.TaskQuery;
import uk.ac.kingston.programming.todomanager.model.User;

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
    
    public TopBarPanel() {
        super(true, true);
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
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        userPanel.add(getLogoutLabel());
        
        headerPanel.add(userPanel, BorderLayout.EAST);
        
        add(headerPanel);
        
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.setOpaque(false);
        
        JLabel paddingLabel = new JLabel(" ");
        paddingLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        actionPanel.add(paddingLabel);
        
        newButton = new JButton("Add Task");
        getNewButton().setFont(Styling.createFontXLarge(true));
        getNewButton().setForeground(Styling.createColor(700));       
        getNewButton().setMnemonic(KeyEvent.VK_A);

        actionPanel.add(getNewButton());
        
        searchLabel = new JLabel("Search: ");
        searchLabel.setFont(Styling.createFontXLarge(true));
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setBorder(BorderFactory.createEmptyBorder(0, 45, 0, 0));
        actionPanel.add(searchLabel);
        
        searchField = new JTextField(18);
        getSearchField().setFont(Styling.createFontXLarge(false));

        actionPanel.add(getSearchField());
               
        sortByLabel = new JLabel("Sort: ");
        sortByLabel.setFont(Styling.createFontXLarge(true));
        sortByLabel.setForeground(Color.WHITE);
        sortByLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        actionPanel.add(sortByLabel);
        
        sortByComboBox = new JComboBox();
        getSortByComboBox().setFont(Styling.createFontXLarge(false));

        DefaultComboBoxModel sortByComboBoxModel = new DefaultComboBoxModel();
        sortByComboBoxModel.addElement(TaskQuery.SORT_BY_TITLE);
        sortByComboBoxModel.addElement(TaskQuery.SORT_BY_ASSIGNEE);
        sortByComboBoxModel.addElement(TaskQuery.SORT_BY_PRIORITY);        
        sortByComboBoxModel.addElement(TaskQuery.SORT_BY_TARGET_DATE);        
        getSortByComboBox().setModel(sortByComboBoxModel);
        getSortByComboBox().setSelectedIndex(3);
        
        actionPanel.add(getSortByComboBox());
        
        sortOrderComboBox = new JComboBox();
        getSortOrderComboBox().setFont(Styling.createFontXLarge(false));
        
        DefaultComboBoxModel sortOrderComboBoxModel = new DefaultComboBoxModel();
        sortOrderComboBoxModel.addElement(TaskQuery.SORT_ORDER_ASC);
        sortOrderComboBoxModel.addElement(TaskQuery.SORT_ORDER_DESC);
        getSortOrderComboBox().setModel(sortOrderComboBoxModel);
        getSortOrderComboBox().setSelectedIndex(1);

        actionPanel.add(getSortOrderComboBox());
        
        add(actionPanel);        
    }

    public void setUser(User user) {
        userLabel.setText(user.getName() + " (" + user.getEmailAddress() + ")");
        getLogoutLabel().setText("Logout");
        updateUI();        
    }

    public void clearUser() {
        userLabel.setText("");
        getLogoutLabel().setText("");
        updateUI();  
    }
    
    /**
     * @return the logoutLabel
     */
    public JLabel getLogoutLabel() {
        return logoutLabel;
    }
    
    /**
     * @return the searchField
     */
    public JTextField getSearchField() {
        return searchField;
    }

    /**
     * @return the sortByComboBox
     */
    public JComboBox getSortByComboBox() {
        return sortByComboBox;
    }

    /**
     * @return the sortOrderComboBox
     */
    public JComboBox getSortOrderComboBox() {
        return sortOrderComboBox;
    }

    /**
     * @return the newButton
     */
    public JButton getNewButton() {
        return newButton;
    }    
}
