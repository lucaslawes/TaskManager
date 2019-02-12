/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.view;

import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

/**
 *
 * @author lucas
 */
public final class InboxPanel extends AppPanel{
    
    private InboxItemsPanel inboxItemsPanel = null;
    
    public InboxPanel() {
        super(true, true);
        
        createChildControls();
    }

    @Override
    public void createChildControls() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));        
        
        if(getInboxItemsPanel() == null) {
            inboxItemsPanel = new InboxItemsPanel();
        }
        JScrollPane scrollPane = new JScrollPane(getInboxItemsPanel(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        add(scrollPane);         
    }

    /**
     * @return the inboxItemsPanel
     */
    public InboxItemsPanel getInboxItemsPanel() {
        return inboxItemsPanel;
    }
}
