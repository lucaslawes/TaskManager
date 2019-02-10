/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.view;

import com.sun.glass.events.KeyEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import uk.ac.kingston.programming.taskmanager.model.SubTask;

/**
 *
 * @author lucas
 */
public final class SubTaskListPanel extends JPanel {
    
    private JTextField titleField;
    private JButton saveButton;
    private JButton newButton;
    private JButton deleteButton;
    private JList subTaskList;
    
    private DefaultListModel listModel;
    
    public SubTaskListPanel() {
                
        setLayout(new BorderLayout());
        
        listModel = new DefaultListModel();

        subTaskList = new JList(listModel);
        subTaskList.setFont(Styling.createFontMedium(false));
        subTaskList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        subTaskList.setLayoutOrientation(JList.VERTICAL);
        subTaskList.setVisibleRowCount(-1);
        subTaskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subTaskList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = subTaskList.getSelectedIndex();
                if(index != -1) {
                    titleField.setText(listModel.get(index).toString());
                }
            }
        });
        
        JScrollPane listScroller = new JScrollPane(subTaskList);
        listScroller.setPreferredSize(new Dimension(450, 200));
        listScroller.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        add(listScroller, BorderLayout.NORTH);
               
        JPanel addSubTaskPanel = new JPanel();
        addSubTaskPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        titleField = new JTextField(15);
        titleField.setFont(Styling.createFontMedium(false));
        
        add(titleField, BorderLayout.CENTER);
        
        newButton = new JButton("New");
        newButton.setFont(Styling.createFontMedium(true));
        newButton.setForeground(Styling.createColor(700));         
        newButton.setMnemonic(KeyEvent.VK_N);        
        
        newButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(titleField.getText().equals("")) {
                    return;
                }
                
                int index = subTaskList.getSelectedIndex() == -1 ? listModel.size() : subTaskList.getSelectedIndex()+1;
                listModel.add(index, new SubTask(0, titleField.getText(), 0));    
               
                titleField.setText("");
                titleField.requestFocus();
            }
        });
        
        saveButton = new JButton("Update");
        saveButton.setFont(Styling.createFontMedium(true));
        saveButton.setForeground(Styling.createColor(700));   
        saveButton.setMnemonic(KeyEvent.VK_U);        
        
        saveButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(titleField.getText().equals("")) {
                    return;
                }

                int selectedIndex = subTaskList.getSelectedIndex();
                
                if(selectedIndex == - 1) {
                    return;
                }
                
                SubTask subTask = (SubTask)listModel.get(selectedIndex);
                subTask.setTitle(titleField.getText());
                subTaskList.updateUI();                
               
                titleField.setText("");
                titleField.requestFocus();
            }
        });
        
        deleteButton = new JButton("Delete");
        deleteButton.setFont(Styling.createFontMedium(true));
        deleteButton.setForeground(Styling.createColor(700));          
        deleteButton.setMnemonic(KeyEvent.VK_D);          
        deleteButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = subTaskList.getSelectedIndex();
                if(index == -1) {
                    return;
                }
                listModel.remove(index);
                
            }
        });
        
        addSubTaskPanel.add(newButton);
        addSubTaskPanel.add(saveButton);
        addSubTaskPanel.add(deleteButton);
        
        add(addSubTaskPanel, BorderLayout.SOUTH);
    }
    
    public void setSubTasks(ArrayList<SubTask> subTasks) {
        listModel.clear();
        for(int i = 0; i < subTasks.size(); i++) {
            listModel.add(i, subTasks.get(i));
        }
    }
    
    public ArrayList<SubTask> getSubTasks() {
        ArrayList<SubTask> subTasks = new ArrayList<>();
        for(int i = 0; i < listModel.size(); i++) {
            SubTask subTask = (SubTask)listModel.get(i);
            subTask.setOrder(i+1);
            subTasks.add(subTask);
        }
        return subTasks;
    }
}
