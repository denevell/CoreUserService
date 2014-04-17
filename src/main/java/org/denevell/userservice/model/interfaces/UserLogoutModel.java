package org.denevell.userservice.model.interfaces;

public interface UserLogoutModel {

	public static final int SUCCESS = 0;
	public static final int FAIL = 1;
	int logout(String authKey);

}
