/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.todomanager.model;

/**
 *
 * @author lucas
 */
public class TaskQuery {
    
    public static final String FILTER_FIELD_STATUS = "Status";
    public static final String FILTER_FIELD_PRIORITY = "Priority";
    public static final String FILTER_FIELD_TARGET_DATE = "Due Date";
    public static final String FILTER_FIELD_ASSIGNEE = "Assigned To";
    
    public static final String FILTER_BY_ALL = "All";
    public static final String FILTER_BY_ACTIVE = "Active";
    public static final String FILTER_BY_COMPLETED = "Completed";
    public static final String FILTER_BY_HIGH = "High";
    public static final String FILTER_BY_MEDIUM = "Medium";
    public static final String FILTER_BY_LOW = "Low";
    public static final String FILTER_BY_TODAY = "Today";
    public static final String FILTER_BY_THIS_WEEK = "This Week";
    public static final String FILTER_BY_THIS_MONTH = "This Month";
    public static final String FILTER_BY_NEXT_MONTH = "Next Month";
    
    public static final String SORT_BY_TITLE = "Title";
    public static final String SORT_BY_ASSIGNEE = "Assignee";
    public static final String SORT_BY_PRIORITY = "Priority";
    public static final String SORT_BY_TARGET_DATE = "Due Date";
    
    public static final String SORT_ORDER_ASC = "Ascending";
    public static final String SORT_ORDER_DESC = "Descending";
    
    private String searchText = "";
    private String sortBy = "";
    private String sortOrder = "";
    private String filterTitle = "";
    private String filterLink = "";    

    public TaskQuery() {
        
    }
    
    public TaskQuery(String searchText, String sortBy, String sortOrder, String filterTitle, String filterLink) {
        this.searchText = searchText;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
        this.filterTitle = filterTitle;
        this.filterLink = filterLink;
    }
    
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
