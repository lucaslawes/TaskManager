/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.view;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import uk.ac.kingston.programming.taskmanager.model.User;

/**
 *
 * @author lucas
 */
public class UserTableModel extends AbstractTableModel {
    
    private List<User> users;
    private final String[] columnNames = {"Name", "Email Address", "Theme"};
    
    public void setData(List<User> users) {
        this.users = users;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = users.get(rowIndex);
        switch(columnIndex) {
            case 0:
                return user.getName();
            case 1:
                return user.getEmailAddress();                
            case 2:
                return user.getTheme();
            default:
                return null;
        }
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;                
            case 2:
                return String.class;
            default:
                return String.class;
        }        
    }
    
}
