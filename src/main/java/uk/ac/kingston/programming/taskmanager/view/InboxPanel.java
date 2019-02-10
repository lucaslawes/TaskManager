/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.view;

import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

/**
 *
 * @author lucas
 */
public final class InboxPanel extends AppPanel{
    
    private InboxItemsPanel inboxItemsPanel = null;
    
    public InboxPanel() {
        super(false, true);

        createChildControls();
    }
    
    @Override
    public void createChildControls() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));        
        
        if(inboxItemsPanel == null) {
            inboxItemsPanel = new InboxItemsPanel();
        }
        JScrollPane scrollPane = new JScrollPane(inboxItemsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        add(scrollPane);         
    }
    
    @Override
    public void onTaskListProviderSet() {
        inboxItemsPanel.setTaskListProvider(getTaskListProvider());
    }    
}
