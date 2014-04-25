package org.denevell.userservice.model.entities;


public class UserLoggedInEntity {
	
	public static final String NAMED_QUERY_FIND_BY_AUTH_KEY = "findByAuthKey";
	public static final String NAMED_QUERY_FIND_BY_ID = "findByUserId";
	
	private long userId;
	private String authKey;

	public UserLoggedInEntity() {
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	
}
