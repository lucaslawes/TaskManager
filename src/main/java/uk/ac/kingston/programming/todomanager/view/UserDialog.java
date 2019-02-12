/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import uk.ac.kingston.programming.todomanager.listener.UserDialogListener;
import uk.ac.kingston.programming.todomanager.listener.UserFormDialogListener;
import uk.ac.kingston.programming.todomanager.model.User;

/**
 *
 * @author lucas
 */
public final class UserDialog extends JDialog implements UserFormDialogListener{
    
    private JTable table;
    private UserTableModel tableModel;
    private JPopupMenu popup;
    private JButton addButton;    
    private final JButton closeButton;
    
    private UserFormDialog userAddEditDialog;
    
    private UserDialogListener userDialogListener;
    
    public UserDialog() {
        
        setTitle("Edit Users");

        ImageIcon image = new ImageIcon(Styling.LOGO_IMAGE);
        setIconImage(image.getImage());
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel userTablePanel = new JPanel(new BorderLayout());
        
        tableModel = new UserTableModel();
        table = new JTable(tableModel){
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
            {
                    Component component = super.prepareRenderer(renderer, row, column);
                    component.setFont(Styling.createFontMedium(false));

                    return component;
            }            
        };

        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getTableHeader().setEnabled(false);
        table.getTableHeader().setFont(Styling.createFontMedium(true));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        scrollPane.setPreferredSize(new Dimension(500, 200));
        userTablePanel.add(scrollPane, BorderLayout.CENTER);
        
        popup = new JPopupMenu();

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JTable table =(JTable) e.getSource();
                int row = table.rowAtPoint(e.getPoint());
                table.getSelectionModel().setSelectionInterval(row, row);
                if(e.getButton() == MouseEvent.BUTTON3) {
                    popup.show(table, e.getX(), e.getY());
                }
                else if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    User user = getUsers().get(row);
                    openAddEditDialog(user);
                }
            }
        });
        
        JMenuItem editItem = new JMenuItem("Edit User");
        editItem.setFont(Styling.createFontLarge(false));
        popup.add(editItem);
        
        editItem.addActionListener((ActionEvent e) -> {
            int row = table.getSelectedRow();
            User user = getUsers().get(row);
            openAddEditDialog(user);
        });         

        JMenuItem removeItem = new JMenuItem("Delete User");
        removeItem.setFont(Styling.createFontLarge(false));
        popup.add(removeItem);
        
        removeItem.addActionListener((ActionEvent e) -> {
            int row = table.getSelectedRow();
            if(JOptionPane.showConfirmDialog(table, "Do you want to delete user '" + getUsers().get(row).getName() + "'?", "Delete User", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                return;
            }
            getUsers().remove(row);
            tableModel.fireTableRowsDeleted(row, row);
        });  

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        addButton = new JButton("Add User");
        addButton.setFont(Styling.createFontMedium(true));
        addButton.setForeground(Styling.createColor(700));     
        addButton.setSize(new Dimension(150,30));
        addButton.addActionListener((ActionEvent e) -> {
            openAddEditDialog(null);
        });
        
        buttonPanel.add(addButton);
        
        userTablePanel.add(buttonPanel, BorderLayout.SOUTH);        
        
        panel.add(userTablePanel, BorderLayout.CENTER);
               
        closeButton = new JButton("Close");
        closeButton.setFont(Styling.createFontXLarge(true));
        closeButton.setForeground(Styling.createColor(700));  
        closeButton.addActionListener((ActionEvent e) -> {
            if(userDialogListener != null) {
                userDialogListener.onCloseClicked();
            }
        });
        
        JPanel outerButtonPanel = new JPanel();
        outerButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        outerButtonPanel.add(closeButton);

        panel.add(outerButtonPanel, BorderLayout.EAST);
        
        add(panel);
        
    }
    
    private ArrayList<User> getUsers() {
        return userDialogListener.getUsers();
    }
    
    public void setData() {
        tableModel.setData(getUsers());      
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
    }
    
    private void openAddEditDialog(User user) {
        if(user == null) {
            userAddEditDialog = new UserFormDialog(null);
        }
        else {
            userAddEditDialog = new UserFormDialog(null, user);
        }
        userAddEditDialog.setResizable(false);
        userAddEditDialog.setAlwaysOnTop(true);
        userAddEditDialog.setBackground(new Color(209,196,233));
        userAddEditDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        userAddEditDialog.setUserFormDialogListener(this);
        userAddEditDialog.pack();
        userAddEditDialog.setLocationRelativeTo(table);
        userAddEditDialog.setVisible(true);                  
    }
    

    @Override
    public void onAddUserCancel() {
        userAddEditDialog.dispose();
    }

    @Override
    public void onAddUser(User user) {
        try
        {
            userDialogListener.onAddUser(user);
        }
        catch(RuntimeException rex){
            JOptionPane.showMessageDialog(this, "Task List data file already exists for '" + user.getName() +"'", "New User", JOptionPane.INFORMATION_MESSAGE);
        }
        
        tableModel.fireTableDataChanged();
        userAddEditDialog.dispose(); 
    }

    @Override
    public void onEditUser(User user) {
        userDialogListener.onEditUser(user);
        tableModel.fireTableDataChanged();        
        userAddEditDialog.dispose();
    }

    @Override
    public void onEditUserCancel() {
        userAddEditDialog.dispose();
    }
 
    @Override
    public boolean userIsUnique(User user) {
        return userDialogListener.userIsUnique(user);
    }  
    
    /**
     * @param userDialogListener the userDialogListener to set
     */
    public void setUserDialogListener(UserDialogListener userDialogListener) {
        this.userDialogListener = userDialogListener;
    }

}
