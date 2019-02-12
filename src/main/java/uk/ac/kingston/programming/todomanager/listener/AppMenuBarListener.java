/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.listener;

/**
 *
 * @author lucas
 */
public interface AppMenuBarListener {
    public void onNewClicked();

    public void onSaveTasksClicked();

    public void onOpenTasksClicked();

    public void onExportTasksClicked();

    public void onExitClicked();

    public void onEditPreferencesClicked();

    public void onAboutClicked();

    public void onEditUsersClicked();
}
