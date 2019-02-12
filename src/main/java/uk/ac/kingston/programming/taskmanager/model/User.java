/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.model;

/**
 *
 * @author lucas
 */
public class User {
    
    private String name = "";
    private String emailAddress = "";
    private String password = "";
    private Boolean isAdministrator = false;
    private String theme = "Blue";
    
    public User() {
        
    }
    
    public User(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }
    
    public User(String name, String emailAddress, String password, Boolean isAdministrator) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.password = password;
        this.isAdministrator = isAdministrator;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the isAdministrator
     */
    public Boolean getIsAdministrator() {
        return isAdministrator;
    }

    /**
     * @param isAdministrator the isAdministrator to set
     */
    public void setIsAdministrator(Boolean isAdministrator) {
        this.isAdministrator = isAdministrator;
    }

    /**
     * @return the theme
     */
    public String getTheme() {
        return theme;
    }

    /**
     * @param theme the theme to set
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }
}
