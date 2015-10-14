package com.imconnect.front.vo;

import java.util.List;

import com.imconnect.core.model.user.User;

public class UserListVO {

	private int pagesCount;
    private long totalContacts;

    private String actionMessage;
    private String searchMessage;

    private List<User> users;

    public UserListVO() {
    }

    public UserListVO(int pages, long totalContacts, List<User> users) {
        this.pagesCount = pages;
        this.users = users;
        this.totalContacts = totalContacts;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public long getTotalContacts() {
        return totalContacts;
    }

    public void setTotalContacts(long totalContacts) {
        this.totalContacts = totalContacts;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    public String getSearchMessage() {
        return searchMessage;
    }

    public void setSearchMessage(String searchMessage) {
        this.searchMessage = searchMessage;
    }
}