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
public class TaskQuery {
    
    private String searchText = "";
    private String sortBy = "";
    private String sortOrder = "";
    private String filterTitle = "";
    private String filterLink = "";    

    /**
     * @return the searchText
     */
    public String getSearchText() {
        return searchText;
    }

    /**
     * @param searchText the searchText to set
     */
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    /**
     * @return the sortBy
     */
    public String getSortBy() {
        return sortBy;
    }

    /**
     * @param sortBy the sortBy to set
     */
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * @return the sortOrder
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the filterTitle
     */
    public String getFilterTitle() {
        return filterTitle;
    }

    /**
     * @param filterTitle the filterTitle to set
     */
    public void setFilterTitle(String filterTitle) {
        this.filterTitle = filterTitle;
    }

    /**
     * @return the filterLink
     */
    public String getFilterLink() {
        return filterLink;
    }

    /**
     * @param filterLink the filterLink to set
     */
    public void setFilterLink(String filterLink) {
        this.filterLink = filterLink;
    }
}
