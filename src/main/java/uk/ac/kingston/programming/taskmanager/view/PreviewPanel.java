/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.view;

/**
 *
 * @author lucas
 */
public final class PreviewPanel extends AppPanel{
    
    public PreviewPanel() {
        super(true, true);
        
        createChildControls();
    }   
    
    @Override
    public void createChildControls() {
        setBackground(Styling.createColor(100));
    }
    
    @Override
    public void onTaskListProviderSet() {
        getTaskListProvider().addTaskListListener(this);
    }  
}
