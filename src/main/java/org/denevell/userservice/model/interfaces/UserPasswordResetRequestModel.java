package org.denevell.userservice.model.interfaces;


public interface UserPasswordResetRequestModel {
	public static final int REQUESTED = 0;
	public static final int EMAIL_NOT_FOUND = 1;
	int requestReset(String recoveryEmail);

}
