/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.view;

import uk.ac.kingston.programming.taskmanager.controller.TaskFormDialogListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import uk.ac.kingston.programming.taskmanager.controller.AppDialogListener;
import uk.ac.kingston.programming.taskmanager.controller.LoginListener;
import uk.ac.kingston.programming.taskmanager.controller.PreferencesDialogListener;
import uk.ac.kingston.programming.taskmanager.controller.TaskListProvider;
import uk.ac.kingston.programming.taskmanager.controller.TopBarListener;
import uk.ac.kingston.programming.taskmanager.model.AppException;
import uk.ac.kingston.programming.taskmanager.model.Task;

/**
 *
 * @author lucas
 */
public final class AppFrame extends JFrame implements LoginListener, TopBarListener, AppDialogListener, TaskFormDialogListener{
    
    private final AppFrame parent;
    
    private LoginDialog loginDialog;
    private TaskFormDialog taskFormDialog;
    
    private final TopBarPanel topBarPanel;
    private final NavBarPanel navBarPanel;
    private final InboxPanel inboxPanel;
    private final PreviewPanel previewPanel;
    private final StatusBarPanel statusBarPanel;
    
    private ActionListener onExitListener;
    private final TaskListProvider taskListProvider;
    
    private String selectedFolderPath = "";
    
    public AppFrame(TaskListProvider taskListProvider) {
        
        this.parent = this;
    
        this.taskListProvider = taskListProvider;
        this.taskListProvider.setAppDialogListener(this);
        
        setLayout(new BorderLayout());
        setJMenuBar(createMenuBar());
        
        setSize(1280,1024);
        
        ImageIcon image = new ImageIcon(Styling.LOGO_IMAGE);
        setIconImage(image.getImage());
        
        setTitle(taskListProvider.getSettings().getTitle());
        
        topBarPanel = new TopBarPanel();
        topBarPanel.setPreferredSize(new Dimension(800,140));
        topBarPanel.setTaskListProvider(taskListProvider);
        topBarPanel.setTopBarListener(this);
        add(topBarPanel, BorderLayout.NORTH);

        navBarPanel = new NavBarPanel();
        navBarPanel.setPreferredSize(new Dimension(230,800));
        navBarPanel.setBackground(Styling.createColor(700));
        navBarPanel.setTaskListProvider(taskListProvider);
        add(navBarPanel, BorderLayout.WEST);

        inboxPanel = new InboxPanel();
        inboxPanel.setPreferredSize(new Dimension(500,800));
        inboxPanel.setTaskListProvider(taskListProvider);
        add(inboxPanel, BorderLayout.CENTER);

        previewPanel = new PreviewPanel();
        previewPanel.setPreferredSize(new Dimension(500,800));
        previewPanel.setTaskListProvider(taskListProvider);
        add(previewPanel, BorderLayout.EAST);

        statusBarPanel = new StatusBarPanel();
        statusBarPanel.setPreferredSize(new Dimension(800,40));
        statusBarPanel.setTaskListProvider(taskListProvider);
        add(statusBarPanel, BorderLayout.SOUTH);
    }
    
    private JMenuBar createMenuBar() {
        
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(Styling.createFontLarge(false));
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);
        
        
        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.setFont(Styling.createFontLarge(false));
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
        newMenuItem.setMnemonic(KeyEvent.VK_N);
        newMenuItem.addActionListener((ActionEvent e) -> {
            if(JOptionPane.showConfirmDialog(parent, "Are you sure you want to overwrite your tasks?", "Open Tasks", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
                return;
            }
            taskListProvider.newTasks();
        });
        
        fileMenu.add(newMenuItem);

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setFont(Styling.createFontLarge(false));
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        saveMenuItem.setMnemonic(KeyEvent.VK_S);
        saveMenuItem.addActionListener((ActionEvent e) -> {
            taskListProvider.saveTasks();
        });
        
        fileMenu.add(saveMenuItem);
        
        fileMenu.addSeparator();
        
        JMenuItem importMenuItem = new JMenuItem("Import");
        importMenuItem.setFont(Styling.createFontLarge(false));
        importMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.ALT_MASK));
        importMenuItem.setMnemonic(KeyEvent.VK_I);
        importMenuItem.addActionListener((ActionEvent e) -> {
            if(JOptionPane.showConfirmDialog(parent, "Are you sure you want to overwrite your tasks?", "Open Tasks", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
                return;
            }
            
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(false);
            fileChooser.setDialogTitle("Select Task List file");
            if(!selectedFolderPath.equals("")) {
                File selectedFolder = new File(selectedFolderPath);
                fileChooser.setCurrentDirectory(selectedFolder);
            }
            fileChooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Task List File (*.json)","json");
            fileChooser.setFileFilter(filter);
            if(fileChooser.showOpenDialog(parent) == JFileChooser.CANCEL_OPTION) {
                return;
            }
            File selectedFile = fileChooser.getSelectedFile();
            taskListProvider.importTasks(selectedFile.getAbsolutePath());
        });
        fileMenu.add(importMenuItem);
        
        JMenuItem exportMenuItem = new JMenuItem("Export");
        exportMenuItem.setFont(Styling.createFontLarge(false));
        exportMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
        exportMenuItem.setMnemonic(KeyEvent.VK_E);
        exportMenuItem.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(false);
            fileChooser.setDialogTitle("Select folder");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if(fileChooser.showSaveDialog(parent) == JFileChooser.CANCEL_OPTION) {
                return;                
            }
            File selectedFile = fileChooser.getSelectedFile();
            selectedFolderPath = selectedFile.getAbsolutePath();
            taskListProvider.exportTasks(selectedFolderPath);
        });
        
        fileMenu.add(exportMenuItem);
        
        fileMenu.addSeparator();
        
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setFont(Styling.createFontLarge(false));
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
        exitMenuItem.setMnemonic(KeyEvent.VK_X);
        exitMenuItem.addActionListener((ActionEvent e) -> {
            if(onExitListener != null) {
                onExitListener.actionPerformed(e);                
            }
        });
        
        fileMenu.add(exitMenuItem);
        
        JMenu editMenu = new JMenu("Edit");
        editMenu.setFont(Styling.createFontLarge(false));
        editMenu.setMnemonic(KeyEvent.VK_E);
        menuBar.add(editMenu);

        JMenuItem preferencesMenuItem = new JMenuItem("Edit Preferences");
        preferencesMenuItem.setFont(Styling.createFontLarge(false));
        preferencesMenuItem.setMnemonic(KeyEvent.VK_P);
        preferencesMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        preferencesMenuItem.addActionListener((ActionEvent e) -> {
            parent.setEnabled(false);
            PreferencesDialog preferencesDialog = new PreferencesDialog(taskListProvider.getUser().getTheme());
            preferencesDialog.setResizable(false);
            preferencesDialog.setAlwaysOnTop(true);
            preferencesDialog.setPreferencesDialogListener(new PreferencesDialogListener() {
                @Override
                public void themeChanged(String theme) {
                    taskListProvider.getUser().setTheme(theme);
                    parent.refresh();
                }

                @Override
                public void saveClicked(String newTheme) {
                    if(!taskListProvider.getUser().getTheme().equals(newTheme)) {
                        taskListProvider.getUser().setTheme(newTheme);
                        parent.refresh();
                    }
                    taskListProvider.saveSettings();
                    parent.setEnabled(true);
                    preferencesDialog.dispose();
                }

                @Override
                public void cancelClicked(String currentTheme) {
                    if(!taskListProvider.getUser().getTheme().equals(currentTheme)) {
                        taskListProvider.getUser().setTheme(currentTheme);
                        parent.refresh();
                    }                    
                    parent.setEnabled(true);
                    preferencesDialog.dispose();                    
                }
            });
            preferencesDialog.pack();
            preferencesDialog.setLocationRelativeTo(parent);
            preferencesDialog.setVisible(true);
        });
        
        editMenu.add(preferencesMenuItem);
        
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setFont(Styling.createFontLarge(false));
        helpMenu.setMnemonic(KeyEvent.VK_H);
        
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.setFont(Styling.createFontLarge(false));
        aboutMenuItem.setMnemonic(KeyEvent.VK_A);
        aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        aboutMenuItem.addActionListener((ActionEvent e) -> {
            parent.setEnabled(false);
            AboutDialog aboutDialog = new AboutDialog(taskListProvider.getSettings());
            aboutDialog.setResizable(false);
            aboutDialog.setAlwaysOnTop(true);
            aboutDialog.setOnOkClicked((ActionEvent e1) -> {
                parent.setEnabled(true);
                aboutDialog.dispose();
            });
            aboutDialog.pack();
            aboutDialog.setLocationRelativeTo(parent);
            aboutDialog.setVisible(true);
        });
             
        helpMenu.add(aboutMenuItem);
        
        menuBar.add(helpMenu);
        
        return menuBar;
    }

    public void refresh() {
        topBarPanel.redraw();
        navBarPanel.redraw();
        inboxPanel.redraw();
        previewPanel.redraw();
        statusBarPanel.redraw();
        taskListProvider.fireOnTasksUpdated();    
    }
    
    public void showLoginDialog() {
        parent.setEnabled(false);
        loginDialog = new LoginDialog();
        loginDialog.setResizable(false);
        loginDialog.setAlwaysOnTop(true);        
        loginDialog.setLoginListener(this);
        loginDialog.pack();
        loginDialog.setLocationRelativeTo(parent);
        loginDialog.setVisible(true);        
    }
    /**
     * @param onExitListener the onExitListener to set
     */
    public void setOnExitListener(ActionListener onExitListener) {
        this.onExitListener = onExitListener;
    }

    @Override
    public void onLoginButtonPressed(String emailAddress, String password) {
        
        boolean loginSuccessful = false;
        
        try
        {
            //loginSuccessful = taskListProvider.loginUser(emailAddress, password);
            loginSuccessful = taskListProvider.loginUser("test@kingston.ac.uk", "topnotch");
        }
        catch(AppException aex)
        {
            JOptionPane.showMessageDialog(loginDialog, aex.getMessage(), "Login", JOptionPane.INFORMATION_MESSAGE);
        }
        
        if(loginSuccessful) {
            refresh();
            setEnabled(true);
            if(loginDialog != null) {
                loginDialog.dispose();
            }
        }
        else {
            loginDialog.setFocus();
        }
    }

    @Override
    public void onCancelButtonPressed() {
        if(loginDialog != null) {
            loginDialog.dispose();
            if(onExitListener != null) {
                onExitListener.actionPerformed(null);
            }
        }
    }

    @Override
    public void onLogoutClicked() {
        taskListProvider.logoutUser();
        showLoginDialog();
    }

    @Override
    public void showNewTaskFormDialog() {
        showTaskFormDialog(null);
    }

    @Override
    public void showEditTaskFormDialog(Task task) {
        showTaskFormDialog(task);
    }

    private void showTaskFormDialog(Task task) {
        parent.setEnabled(false);
        if(task == null) {
            taskFormDialog = new TaskFormDialog(this);    
        }
        else {
            taskFormDialog = new TaskFormDialog(this, task);    
        }
        taskFormDialog.setResizable(false);
        taskFormDialog.setUndecorated(false);
        taskFormDialog.setBackground(new Color(209,196,233));
        taskFormDialog.setSize(800,600);
        taskFormDialog.setPreferredSize(new Dimension(800,600));
        taskFormDialog.setMinimumSize(new Dimension(800,600));
        taskFormDialog.setMaximumSize(new Dimension(800,600));
        taskFormDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        taskFormDialog.setTaskFormDialogListener(this);
        taskFormDialog.setLocationRelativeTo(this);        
        taskFormDialog.pack();
        taskFormDialog.setVisible(true);     
    }
    
    @Override
    public void onAddTaskCancel() {
        parent.setEnabled(true);
        taskFormDialog.dispose();
    }

    @Override
    public void onAddTask(Task task) {
        taskListProvider.addTaskCompleted(task);
        parent.setEnabled(true);
        taskFormDialog.dispose();
    }

    @Override
    public void onEditTask(Task task) {
        taskListProvider.editTaskCompleted(task);
        parent.setEnabled(true);
        taskFormDialog.dispose(); 
    }

    @Override
    public void onEditTaskCancel() {
        parent.setEnabled(true);
        taskFormDialog.dispose();
    }
}
