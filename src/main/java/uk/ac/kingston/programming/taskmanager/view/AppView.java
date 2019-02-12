/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.view;

import uk.ac.kingston.programming.taskmanager.controller.TaskFormDialogController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import uk.ac.kingston.programming.taskmanager.controller.AppController;
import uk.ac.kingston.programming.taskmanager.controller.AppMenuBarController;
import uk.ac.kingston.programming.taskmanager.controller.LoginDialogController;
import uk.ac.kingston.programming.taskmanager.model.Task;

/**
 *
 * @author lucas
 */
public final class AppView extends JFrame {
    
    private AppMenuBar appMenuBar;
    
    private TopBarPanel topBarPanel;
    private NavBarPanel navBarPanel;
    private InboxPanel inboxPanel;
    private PreviewPanel previewPanel;
    private StatusBarPanel statusBarPanel;
    
    private final AppController appController = new AppController();
    private final AppMenuBarController appMenuBarController = new AppMenuBarController();
    private final LoginDialogController loginDialogController = new LoginDialogController();
    private final TaskFormDialogController taskFormDialogController = new TaskFormDialogController();
    
    private static AppView instance = null;
    
    public static AppView getInstance()
    {
        if (instance == null) instance = new AppView();
        return instance;
    }    

    private AppView() {

    }
    
    public void createChildControls() {
        
        setTitle("Task Manager");
        
        ImageIcon image = new ImageIcon(Styling.LOGO_IMAGE);
        setIconImage(image.getImage());
                
        setLayout(new BorderLayout());
        
        appMenuBar = new AppMenuBar();
        getAppMenuBar().setAppMenuBarListener(appMenuBarController);
        
        setJMenuBar(getAppMenuBar());
        
        topBarPanel = new TopBarPanel();
        topBarPanel.setPreferredSize(new Dimension(800,110));
        add(topBarPanel, BorderLayout.NORTH);
        
        navBarPanel = new NavBarPanel();
        navBarPanel.setPreferredSize(new Dimension(160, 520));
        add(navBarPanel, BorderLayout.WEST);
        
        inboxPanel = new InboxPanel();
        inboxPanel.setPreferredSize(new Dimension(400, 520));
        add(inboxPanel, BorderLayout.CENTER);
        
        previewPanel = new PreviewPanel();
        previewPanel.setPreferredSize(new Dimension(400, 520));
        add(previewPanel, BorderLayout.EAST);
        
        statusBarPanel = new StatusBarPanel();
        statusBarPanel.setPreferredSize(new Dimension(800,35));        
        add(statusBarPanel, BorderLayout.SOUTH);
        
        appController.bind(false); 
        
        showLoginDialog();
    }
    
    public void refresh() {
        topBarPanel.redraw();
        navBarPanel.redraw();
        inboxPanel.redraw();
        previewPanel.redraw();
        statusBarPanel.redraw();
        appController.bind(true);
    }
    
    public void showLoginDialog() {
        setEnabled(false);
        LoginDialog loginDialog = LoginDialog.getInstance();
        loginDialog.prepare();
        loginDialog.setResizable(false);
        loginDialog.setAlwaysOnTop(true);        
        loginDialog.setLoginDialogListener(loginDialogController);
        loginDialog.pack();
        loginDialog.setLocationRelativeTo(this);
        loginDialog.setFocus();
        loginDialog.setVisible(true);        
    }   
    
    public void showTaskFormDialog(Task task) {
        setEnabled(false);
        TaskFormDialog taskFormDialog = TaskFormDialog.getInstance();    
        taskFormDialog.setEditMode(task != null);
        taskFormDialog.setTask(task);
        taskFormDialog.prepareForm();
        taskFormDialog.setTaskFormDialogListener(taskFormDialogController);
        taskFormDialog.setResizable(false);
        taskFormDialog.setSize(800,600);
        taskFormDialog.setPreferredSize(new Dimension(800,600));
        taskFormDialog.setMinimumSize(new Dimension(800,600));
        taskFormDialog.setMaximumSize(new Dimension(800,600));
        taskFormDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        taskFormDialog.pack();        
        taskFormDialog.setLocationRelativeTo(this);        
        taskFormDialog.setVisible(true);     
    }    
    /**
     * @return the topBarPanel
     */
    public TopBarPanel getTopBarPanel() {
        return topBarPanel;
    }

    /**
     * @return the navBarPanel
     */
    public NavBarPanel getNavBarPanel() {
        return navBarPanel;
    }

    /**
     * @return the inboxPanel
     */
    public InboxPanel getInboxPanel() {
        return inboxPanel;
    }

    /**
     * @return the previewPanel
     */
    public PreviewPanel getPreviewPanel() {
        return previewPanel;
    }

    /**
     * @return the statusBarPanel
     */
    public StatusBarPanel getStatusBarPanel() {
        return statusBarPanel;
    }   

    /**
     * @return the appMenuBar
     */
    public AppMenuBar getAppMenuBar() {
        return appMenuBar;
    }    
}
