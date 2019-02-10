/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.view;

import uk.ac.kingston.programming.taskmanager.controller.TaskFormDialogListener;
import com.sun.glass.events.KeyEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Properties;
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
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import uk.ac.kingston.programming.taskmanager.model.SubTask;
import uk.ac.kingston.programming.taskmanager.model.Task;

/**
 *
 * @author lucas
 */
public final class TaskFormDialog extends JDialog {
    
    private JLabel titleLabel;
    private JLabel assigneeLabel;
    private JTextField titleField;
    private JTextField assigneeField;
    private JLabel targetDateLabel;
    private JDatePickerImpl targetDatePicker;
    private JLabel priorityLabel;
    private JComboBox priorityComboBox;
    private JLabel completedLabel;
    private JCheckBox completedCheck;
    private JLabel subTaskListLabel;
    private SubTaskListPanel subTaskListPanel;
    
    protected JButton cancelButton;
    protected JButton addEditButton;
    
    TaskFormDialogListener taskFormDialogListener;
    
    private Task task = new Task();
    private boolean editMode = false;
    
    JFrame owner;
    JDialog parent;
            
    public TaskFormDialog(JFrame owner, Task task) {
        super(owner);
        parent = this;
        this.task = task;
        this.editMode = true;
        createChildComponents();
    }
    
    public TaskFormDialog(JFrame owner) {
        super(owner);
        parent = this;
        createChildComponents();
    }

    public void createChildComponents() {
        
        ImageIcon image = new ImageIcon(Styling.LOGO_IMAGE);
        setIconImage(image.getImage());
        
        setTitle(editMode ? "Edit Task" : "New Task");

        titleLabel = new JLabel("Title: ");
        titleLabel.setFont(Styling.createFontMedium(true));
        
        titleField = new JTextField(40);
        titleField.setFont(Styling.createFontMedium(false));
               
        titleLabel.setDisplayedMnemonic(KeyEvent.VK_T);
        titleLabel.setLabelFor(titleField);        
        
        assigneeLabel = new JLabel("Assign To: ");
        assigneeLabel.setFont(Styling.createFontMedium(true));
        
        assigneeField = new JTextField(20);
        assigneeField.setFont(Styling.createFontMedium(false));
        
        assigneeLabel.setDisplayedMnemonic(KeyEvent.VK_A);
        assigneeLabel.setLabelFor(assigneeField);
        
        targetDateLabel = new JLabel("Target Date: ");
        targetDateLabel.setFont(Styling.createFontMedium(true));
        
        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        datePanel.setFont(new Font("Segoe UI", 0, 14));
        targetDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        targetDatePicker.setFont(Styling.createFontMedium(false));
        targetDatePicker.getJFormattedTextField().setFont(Styling.createFontMedium(false));
        targetDatePicker.getJFormattedTextField().setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH)));
        
        completedLabel = new JLabel("Completed: ");
        completedLabel.setFont(Styling.createFontMedium(true));
        
        completedCheck = new JCheckBox();
        completedCheck.setFont(Styling.createFontMedium(false));

        priorityLabel = new JLabel("Priority: ");
        priorityLabel.setFont(Styling.createFontMedium(true));
        
        priorityComboBox = new JComboBox();
        priorityComboBox.setFont(Styling.createFontMedium(false));
        
        DefaultComboBoxModel priorityComboBoxModel = new DefaultComboBoxModel();
        priorityComboBoxModel.addElement("High");
        priorityComboBoxModel.addElement("Medium");
        priorityComboBoxModel.addElement("Low");
        priorityComboBox.setModel(priorityComboBoxModel);

        subTaskListLabel = new JLabel("Sub Tasks: ");
        subTaskListLabel.setFont(Styling.createFontMedium(true));
        
        subTaskListPanel = new SubTaskListPanel();
        
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(Styling.createFontXLarge(true));
        cancelButton.setForeground(Styling.createColor(700));         
        cancelButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               if(taskFormDialogListener != null) {
                   taskFormDialogListener.onAddTaskCancel();
               }
            }
        });
        
        addEditButton = new JButton(editMode ? "Save Task" : "Add Task");
        addEditButton.setFont(Styling.createFontXLarge(true));
        addEditButton.setForeground(Styling.createColor(700));  
        addEditButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                if(title.equals("")) {
                    JOptionPane.showMessageDialog(parent, "Title must be provided", editMode ? "Edit Task" : "New Task", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                String assignee = assigneeField.getText();
                if(assignee.equals("")) {
                    JOptionPane.showMessageDialog(parent, "Assignee must be provided", editMode ? "Edit Task" : "New Task", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                String targetDateAsString = targetDatePicker.getJFormattedTextField().getText();
                if(targetDateAsString.equals("")) {
                    JOptionPane.showMessageDialog(parent, "Target Date must be provided", editMode ? "Edit Task" : "New Task", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                LocalDate targetDate = LocalDate.parse(targetDateAsString, DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH));
                int priority = priorityComboBox.getSelectedIndex()+1;
                boolean completed = completedCheck.isSelected();
                
                if(!editMode) {
                    task = new Task(0, title, assignee, targetDate);
                    task.setPriority(priority);
                    task.setCompleted(completed);
                }
                else {
                    task.setTitle(title);
                    task.setAssignee(assignee);
                    task.setTargetDate(targetDate);
                    task.setPriority(priority);
                    task.setCompleted(completed);
                }
                task.getSubTasks().clear();
                for(SubTask subTask : subTaskListPanel.getSubTasks()) {
                    task.getSubTasks().add(subTask);
                }
                if(taskFormDialogListener != null) {
                    if(!editMode) {
                        taskFormDialogListener.onAddTask(task);
                    } 
                    else {
                        taskFormDialogListener.onEditTask(task);
                    }
                }
            }
        });
        
        layoutComponents(); 
        
        if(editMode) {
            titleField.setText(task.getTitle());
            assigneeField.setText(task.getAssignee());
            targetDatePicker.getJFormattedTextField().setText(task.getTargetDateAsString());
            priorityComboBox.setSelectedIndex(task.getPriority()-1);
            completedCheck.setSelected(task.isCompleted());
            subTaskListPanel.setSubTasks(task.getSubTasks());
        }
    }
    
    public void layoutComponents() {
        setLayout(new BorderLayout());
               
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(addEditButton);
        buttonPanel.add(cancelButton);
        
        addEditButton.setPreferredSize(new Dimension(120,40));
        cancelButton.setPreferredSize(new Dimension(120,40));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 5, 0, 20));
        buttonPanel.setPreferredSize(new Dimension(150, 200));
 
        JPanel formPanel = new JPanel();
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
        formPanel.add(titleLabel, gc);
        
        gc.gridx = 1;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(30,0,10,0);
        formPanel.add(titleField, gc);
        
        // Second Row
        gc.gridy++;        
        
        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,10,5);
        formPanel.add(assigneeLabel, gc);
        
        gc.gridy = 1;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,0,10,0);
        formPanel.add(assigneeField, gc);
        
        // Third Row
        gc.gridy++;        
        
        gc.weightx = 1;
        gc.weighty = 0.2;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0,0,10,5);
        formPanel.add(targetDateLabel, gc);
        
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(0,0,10,0);
        formPanel.add(targetDatePicker, gc);        
        
        // Fourth Row
        gc.gridy++;        
        
        gc.weightx = 1;
        gc.weighty = 0.2;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0,0,10,5);
        formPanel.add(priorityLabel, gc);
        
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(0,0,10,0);
        formPanel.add(priorityComboBox, gc); 

        // Fifth Row
        gc.gridy++;        
        
        gc.weightx = 1;
        gc.weighty = 0.2;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0,0,10,5);
        formPanel.add(completedLabel, gc);
        
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(0,0,10,0);
        formPanel.add(completedCheck, gc); 

        // Sixth Row
        gc.gridy++;        
        
        gc.weightx = 1;
        gc.weighty = 0.2;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0,0,10,5);
        formPanel.add(subTaskListLabel, gc);
        
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(0,0,10,0);
        formPanel.add(subTaskListPanel, gc);
        
        add(formPanel, BorderLayout.CENTER);
        
        add(buttonPanel, BorderLayout.EAST);
        
    }
        
    public void setTaskFormDialogListener(TaskFormDialogListener taskFormDialogListener) {
        this.taskFormDialogListener = taskFormDialogListener;
    }
}
