/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.listener;

import uk.ac.kingston.programming.todomanager.view.LoginDialog;

/**
 *
 * @author lucas
 */
public interface LoginDialogListener {

    public void onLoginButtonPressed(String emailAddress, String password);
    
    public void onCancelButtonPressed();
    
}
