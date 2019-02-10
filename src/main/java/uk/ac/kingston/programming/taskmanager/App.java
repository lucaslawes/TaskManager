/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager;

import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import uk.ac.kingston.programming.taskmanager.data.SettingsManager;
import uk.ac.kingston.programming.taskmanager.data.FileTaskListProvider;
import uk.ac.kingston.programming.taskmanager.view.AppFrame;
import uk.ac.kingston.programming.taskmanager.controller.TaskListProvider;
import uk.ac.kingston.programming.taskmanager.view.Styling;

/**
 *
 * @author lucas
 */
public class App {
    
    private static AppFrame appFrame;
    
    public static void main(String[] args) {
        SettingsManager.load();
        TaskListProvider taskListProvider = (TaskListProvider)new FileTaskListProvider();
        Styling.setStylingListener(() -> {
            return taskListProvider.getUser().getTheme();
        });
        SwingUtilities.invokeLater(() -> {
            appFrame = new AppFrame(taskListProvider);
            appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            appFrame.setOnExitListener((ActionEvent e) -> {
                appFrame.dispose();
            });
            appFrame.pack();
            appFrame.setLocationRelativeTo(null);
            appFrame.setVisible(true); 
            appFrame.showLoginDialog();
        });
    }
}
