/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import uk.ac.kingston.programming.todomanager.listener.AppMenuBarListener;

/**
 *
 * @author lucas
 */
public final class AppMenuBar extends JMenuBar{
    
    private JMenuItem editUsersMenuItem;
    
    private AppMenuBarListener appMenuBarListener;
    
    public AppMenuBar() {
        createChildControls();
    }
    
    private void createChildControls() {
        
        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(Styling.createFontLarge(false));
        fileMenu.setMnemonic(KeyEvent.VK_F);
        add(fileMenu);
        
        
        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.setFont(Styling.createFontLarge(false));
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        newMenuItem.setMnemonic(KeyEvent.VK_N);
        newMenuItem.addActionListener((ActionEvent e) -> {
            if(appMenuBarListener != null) {
                appMenuBarListener.onNewClicked();
            }
        });
        
        fileMenu.add(newMenuItem);
      
        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setFont(Styling.createFontLarge(false));
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        openMenuItem.setMnemonic(KeyEvent.VK_O);
        openMenuItem.addActionListener((ActionEvent e) -> {
            if(appMenuBarListener != null) {
                appMenuBarListener.onOpenTasksClicked();
            }
        });
        fileMenu.add(openMenuItem);
        
        fileMenu.addSeparator();
        
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setFont(Styling.createFontLarge(false));
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        saveMenuItem.setMnemonic(KeyEvent.VK_S);
        saveMenuItem.addActionListener((ActionEvent e) -> {
            if(appMenuBarListener != null) {
                appMenuBarListener.onSaveTasksClicked();
            }
        });
        
        fileMenu.add(saveMenuItem);
        
        JMenuItem exportMenuItem = new JMenuItem("Export");
        exportMenuItem.setFont(Styling.createFontLarge(false));
        exportMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        exportMenuItem.setMnemonic(KeyEvent.VK_E);
        exportMenuItem.addActionListener((ActionEvent e) -> {
            if(appMenuBarListener != null) {
                appMenuBarListener.onExportTasksClicked();
            }
        });
        
        fileMenu.add(exportMenuItem);
        
        fileMenu.addSeparator();
        
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setFont(Styling.createFontLarge(false));
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        exitMenuItem.setMnemonic(KeyEvent.VK_X);
        exitMenuItem.addActionListener((ActionEvent e) -> {
            if(appMenuBarListener != null) {
                appMenuBarListener.onExitClicked();
            }
        });
        
        fileMenu.add(exitMenuItem);
        
        JMenu editMenu = new JMenu("Edit");
        editMenu.setFont(Styling.createFontLarge(false));
        editMenu.setMnemonic(KeyEvent.VK_E);
        add(editMenu);

        editUsersMenuItem = new JMenuItem("Edit Users");
        getEditUsersMenuItem().setFont(Styling.createFontLarge(false));
        getEditUsersMenuItem().setMnemonic(KeyEvent.VK_U);
        getEditUsersMenuItem().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        getEditUsersMenuItem().addActionListener((ActionEvent e) -> {
            if(appMenuBarListener != null) {
                appMenuBarListener.onEditUsersClicked();
            }
        });
        
        editMenu.add(getEditUsersMenuItem());
        
        JMenuItem preferencesMenuItem = new JMenuItem("Edit Preferences");
        preferencesMenuItem.setFont(Styling.createFontLarge(false));
        preferencesMenuItem.setMnemonic(KeyEvent.VK_P);
        preferencesMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        preferencesMenuItem.addActionListener((ActionEvent e) -> {
            if(appMenuBarListener != null) {
                appMenuBarListener.onEditPreferencesClicked();
            }
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
            if(appMenuBarListener != null) {
                appMenuBarListener.onAboutClicked();
            }
        });
             
        helpMenu.add(aboutMenuItem);
        
        add(helpMenu);
    }    

    /**
     * @param appMenuBarListener the appMenuBarListener to set
     */
    public void setAppMenuBarListener(AppMenuBarListener appMenuBarListener) {
        this.appMenuBarListener = appMenuBarListener;
    }

    /**
     * @return the editUsersMenuItem
     */
    public JMenuItem getEditUsersMenuItem() {
        return editUsersMenuItem;
    }
}
