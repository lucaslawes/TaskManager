/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.controller;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import uk.ac.kingston.programming.taskmanager.data.SettingsManager;
import uk.ac.kingston.programming.taskmanager.data.TaskListManager;
import uk.ac.kingston.programming.taskmanager.listener.AppMenuBarListener;
import uk.ac.kingston.programming.taskmanager.listener.PreferencesDialogListener;
import uk.ac.kingston.programming.taskmanager.listener.UserDialogListener;
import uk.ac.kingston.programming.taskmanager.model.User;
import uk.ac.kingston.programming.taskmanager.view.AboutDialog;
import uk.ac.kingston.programming.taskmanager.view.AppView;
import uk.ac.kingston.programming.taskmanager.view.PreferencesDialog;
import uk.ac.kingston.programming.taskmanager.view.UserDialog;

/**
 *
 * @author lucas
 */
public class AppMenuBarController implements AppMenuBarListener{

    private String selectedFolderPath = "";
    
    @Override
    public void onNewClicked() {
            if(JOptionPane.showConfirmDialog(AppView.getInstance(), "Are you sure you want to overwrite your tasks?", "New Task List", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
                return;
            }
            TaskListManager.getInstance().newTasks();
    }

    @Override
    public void onSaveTasksClicked() {
        TaskListManager.getInstance().saveTasks();
    }

    @Override
    public void onOpenTasksClicked() {
        if(JOptionPane.showConfirmDialog(AppView.getInstance(), "Are you sure you want to overwrite your tasks?", "Open Task List", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setDialogTitle("Select Task List file to import");
        if(!selectedFolderPath.equals("")) {
            File selectedFolder = new File(selectedFolderPath);
            fileChooser.setCurrentDirectory(selectedFolder);
        }
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Task List File (*.json)","json");
        fileChooser.setFileFilter(filter);
        if(fileChooser.showOpenDialog(AppView.getInstance()) == JFileChooser.CANCEL_OPTION) {
            return;
        }
        File selectedFile = fileChooser.getSelectedFile();
        TaskListManager.getInstance().importTasks(selectedFile.getAbsolutePath());        
    }

    @Override
    public void onExportTasksClicked() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setDialogTitle("Select Export Folder");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(fileChooser.showSaveDialog(AppView.getInstance()) == JFileChooser.CANCEL_OPTION) {
            return;                
        }
        File selectedFile = fileChooser.getSelectedFile();
        selectedFolderPath = selectedFile.getAbsolutePath();
        TaskListManager.getInstance().exportTasks(selectedFolderPath);        
    }

    @Override
    public void onExitClicked() {
        if(JOptionPane.showConfirmDialog(AppView.getInstance(), "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
            return;
        }        
        AppView.getInstance().dispose();
    }

    @Override
    public void onEditPreferencesClicked() {
        AppView.getInstance().setEnabled(false);
        PreferencesDialog preferencesDialog = PreferencesDialog.getInstance();
        preferencesDialog.setUser(TaskListManager.getInstance().getUser());
        preferencesDialog.setResizable(false);
        preferencesDialog.setAlwaysOnTop(true);
        preferencesDialog.setPreferencesDialogListener(new PreferencesDialogListener() {
            @Override
            public void themeChanged(String theme) {
                if(theme.equals(TaskListManager.getInstance().getUser().getTheme())){
                    return;
                }
                TaskListManager.getInstance().getUser().setTheme(theme);
                AppView.getInstance().refresh();
            }

            @Override
            public void saveClicked(String newTheme) {
                if(!TaskListManager.getInstance().getUser().getTheme().equals(newTheme)) {
                    TaskListManager.getInstance().getUser().setTheme(newTheme);
                    AppView.getInstance().refresh();
                }
                TaskListManager.getInstance().saveSettings();
                AppView.getInstance().setEnabled(true);
                preferencesDialog.dispose();
            }

            @Override
            public void cancelClicked(String currentTheme) {
                if(!TaskListManager.getInstance().getUser().getTheme().equals(currentTheme)) {
                    TaskListManager.getInstance().getUser().setTheme(currentTheme);
                    AppView.getInstance().refresh();
                }                    
                AppView.getInstance().setEnabled(true);
                preferencesDialog.dispose();                    
            }
        });
        preferencesDialog.pack();
        preferencesDialog.setLocationRelativeTo(AppView.getInstance());
        preferencesDialog.setVisible(true);        
    }

    @Override
    public void onAboutClicked() {
        AppView.getInstance().setEnabled(false);
        AboutDialog aboutDialog = AboutDialog.getInstance();
        aboutDialog.setSettings(TaskListManager.getInstance().getSettings());
        aboutDialog.createChildControls();
        aboutDialog.setResizable(false);
        aboutDialog.setAlwaysOnTop(true);
        aboutDialog.setOnOkClicked((ActionEvent e1) -> {
            AppView.getInstance().setEnabled(true);
            AboutDialog.getInstance().dispose();
        });
        aboutDialog.pack();
        aboutDialog.setLocationRelativeTo(AppView.getInstance());
        aboutDialog.setVisible(true);
    }

    @Override
    public void onEditUsersClicked() {
        AppView.getInstance().setEnabled(false);
        UserDialog userDialog = new UserDialog();
        userDialog.setResizable(false);
        userDialog.setAlwaysOnTop(true);
        userDialog.setUserDialogListener(new UserDialogListener() {
            @Override
            public void onCloseClicked() {
                AppView.getInstance().setEnabled(true);
                userDialog.dispose();
            }

            @Override
            public ArrayList<User> getUsers() {
                return SettingsManager.getInstance().getSettings().getUsers();
            }
            
            @Override
            public void onAddUser(User user) {
                SettingsManager.getInstance().createTaskListAndSave(user);
                SettingsManager.getInstance().getSettings().getUsers().add(user);
                SettingsManager.getInstance().save();   
            }    
            
            @Override
            public void onEditUser(User user) {
                SettingsManager.getInstance().save();
                if(user.equals(TaskListManager.getInstance().getUser())) {
                    AppView.getInstance().refresh();
                }
            }
            
            @Override
            public boolean userIsUnique(User user) {
                return SettingsManager.getInstance().getSettings().userIsUnique(user);
            }
        });
        userDialog.setData();
        userDialog.pack();
        userDialog.setLocationRelativeTo(AppView.getInstance());        
        userDialog.setVisible(true);
    }
    
}
