/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
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
import uk.ac.kingston.programming.taskmanager.data.SettingsManager;
import uk.ac.kingston.programming.taskmanager.listener.UserFormDialogListener;
import uk.ac.kingston.programming.taskmanager.model.User;

/**
 *
 * @author lucas
 */
public final class UserTablePanel extends JPanel{
    
    private JTable table;
    private UserTableModel tableModel;
    private JPopupMenu popup;
    private JButton addButton;
    
    private UserFormDialog userAddEditDialog;
    
    private UserFormDialogListener userFormDialogListener;
    
    public UserTablePanel() {
        
        setLayout(new BorderLayout());
        
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
        add(scrollPane, BorderLayout.CENTER);
        
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
                    User user = SettingsManager.getInstance().getSettings().getUsers().get(row);
                    openAddEditDialog(user);
                }
            }
        });
        
        JMenuItem editItem = new JMenuItem("Edit User");
        editItem.setFont(Styling.createFontLarge(false));
        popup.add(editItem);
        
        editItem.addActionListener((ActionEvent e) -> {
            int row = table.getSelectedRow();
            User user = SettingsManager.getInstance().getSettings().getUsers().get(row);
            openAddEditDialog(user);
        });         

        JMenuItem removeItem = new JMenuItem("Delete User");
        removeItem.setFont(Styling.createFontLarge(false));
        popup.add(removeItem);
        
        removeItem.addActionListener((ActionEvent e) -> {
            int row = table.getSelectedRow();
            if(JOptionPane.showConfirmDialog(table, "Do you want to delete user '" + SettingsManager.getInstance().getSettings().getUsers().get(row).getName() + "'?", "Delete User", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                return;
            }
            SettingsManager.getInstance().getSettings().getUsers().remove(row);
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
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public void setData(List<User> users) {
        tableModel.setData(users);      
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
        userAddEditDialog.setUndecorated(false);
        userAddEditDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        userAddEditDialog.setUserFormDialogListener(userFormDialogListener);
        userAddEditDialog.pack();
        userAddEditDialog.setLocationRelativeTo(table);
        userAddEditDialog.setVisible(true);                  
    }

    /**
     * @param userFormDialogListener the userFormDialogListener to set
     */
    public void setUserFormDialogListener(UserFormDialogListener userFormDialogListener) {
        this.userFormDialogListener = userFormDialogListener;
    }
}
