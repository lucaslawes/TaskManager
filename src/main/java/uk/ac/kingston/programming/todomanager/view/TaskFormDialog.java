/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.view;

import com.sun.glass.events.KeyEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import uk.ac.kingston.programming.todomanager.listener.TaskFormDialogListener;
import uk.ac.kingston.programming.todomanager.model.Task;

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

    private static TaskFormDialog instance;
    
    public static TaskFormDialog getInstance() {
        if(instance == null) {
            instance = new TaskFormDialog();
        }
        return instance;
    }
    
    private TaskFormDialog() {
        createChildControls();
    }
    
    private void createChildControls() {
        
        ImageIcon image = new ImageIcon(Styling.LOGO_IMAGE);
        setIconImage(image.getImage());

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
        cancelButton.addActionListener((ActionEvent e) -> {
            if(taskFormDialogListener != null) {
                taskFormDialogListener.onCancelButtonClicked();
            }
        });
        
        addEditButton = new JButton();
        addEditButton.setFont(Styling.createFontXLarge(true));
        addEditButton.setForeground(Styling.createColor(700));  
        addEditButton.addActionListener((ActionEvent e) -> {
            String title1 = titleField.getText();
            
            if (title1.equals("")) {
                JOptionPane.showMessageDialog(getParent(), "Title must be provided", isEditMode() ? "Edit Task" : "New Task", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            String assignee = assigneeField.getText();
            
            if(assignee.equals("")) {
                JOptionPane.showMessageDialog(getParent(), "Assignee must be provided", isEditMode() ? "Edit Task" : "New Task", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            String targetDateAsString = targetDatePicker.getJFormattedTextField().getText();
            
            if(targetDateAsString.equals("")) {
                JOptionPane.showMessageDialog(getParent(), "Target Date must be provided", isEditMode() ? "Edit Task" : "New Task", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            LocalDate targetDate = LocalDate.parse(targetDateAsString, DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH));
            int priority = priorityComboBox.getSelectedIndex()+1;
            
            boolean completed = completedCheck.isSelected();
            
            if (!isEditMode()) {
                setTask(new Task(0, title1, assignee, targetDate));
                getTask().setPriority(priority);
                getTask().setCompleted(completed);
            } else {
                getTask().setTitle(title1);
                getTask().setAssignee(assignee);
                getTask().setTargetDate(targetDate);
                getTask().setPriority(priority);
                getTask().setCompleted(completed);
            }
            getTask().getSubTasks().clear();
            subTaskListPanel.getSubTasks().forEach((subTask) -> {
                getTask().getSubTasks().add(subTask);
            });
            if(taskFormDialogListener != null) {
                if(!isEditMode()) {
                    taskFormDialogListener.onAddTask(getTask());
                }
                else {
                    taskFormDialogListener.onEditTask(getTask());
                }
            }
        });
        
        layoutComponents(); 

    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout());
               
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(addEditButton);
        buttonPanel.add(cancelButton);
        
        addEditButton.setPreferredSize(new Dimension(100,30));
        cancelButton.setPreferredSize(new Dimension(100,30));
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

    /**
     * @return the task
     */
    public Task getTask() {
        return this.task;
    }
    
    /**
     * @param task the task to set
     */
    public void setTask(Task task) {
        
        if(task == null) {
            this.task = new Task();
        }
        else {
            this.task = task;
        }
    }

    /**
     * @return the editMode
     */
    public boolean isEditMode() {
        return editMode;
    }

    /**
     * @param editMode the editMode to set
     */
    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
    
    public void prepareForm() {
        setTitle(isEditMode() ? "Edit Task" : "New Task");
        addEditButton.setText(isEditMode() ? "Save Task" : "Add Task");
        addEditButton.setForeground(Styling.createColor(700));
        cancelButton.setForeground(Styling.createColor(700));
        titleField.setText(getTask().getTitle());
        assigneeField.setText(getTask().getAssignee());
        targetDatePicker.getJFormattedTextField().setText(getTask().getTargetDateAsString());
        priorityComboBox.setSelectedIndex(getTask().getPriority()-1);
        completedCheck.setSelected(getTask().isCompleted());
        subTaskListPanel.setSubTasks(getTask().getSubTasks());        
    }
    
    public void setTaskFormDialogListener(TaskFormDialogListener taskFormDialogListener) {
        this.taskFormDialogListener = taskFormDialogListener;
    }    
}
