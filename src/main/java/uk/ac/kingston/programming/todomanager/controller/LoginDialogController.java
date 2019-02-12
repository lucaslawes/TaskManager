/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.controller;

import javax.swing.JOptionPane;
import uk.ac.kingston.programming.todomanager.data.TaskListManager;
import uk.ac.kingston.programming.todomanager.listener.LoginDialogListener;
import uk.ac.kingston.programming.todomanager.model.AppException;
import uk.ac.kingston.programming.todomanager.view.AppView;
import uk.ac.kingston.programming.todomanager.view.LoginDialog;

/**
 *
 * @author lucas
 */
public class LoginDialogController implements LoginDialogListener {
    
    @Override
    public void onLoginButtonPressed(String emailAddress, String password) {
        
        boolean loginSuccessful = false;
        
        try
        {
            if(emailAddress.length() == 0) {
                 TaskListManager.getInstance().loginUser("test@kingston.ac.uk", "topnotch");
            }
            else {
                TaskListManager.getInstance().loginUser(emailAddress, password);
            }
            
            loginSuccessful = true;
        }
        catch(AppException aex) {
            JOptionPane.showMessageDialog(LoginDialog.getInstance(), aex.getMessage(), "Login", JOptionPane.INFORMATION_MESSAGE);
        }
        
        if(loginSuccessful) {
            LoginDialog.getInstance().dispose();
        }
        else {
            LoginDialog.getInstance().setFocus();
        }        
    }

    @Override
    public void onCancelButtonPressed() {
        LoginDialog.getInstance().dispose();
        AppView.getInstance().dispose();
    } 
    
}
