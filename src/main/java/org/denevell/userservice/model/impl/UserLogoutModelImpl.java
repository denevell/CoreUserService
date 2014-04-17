package org.denevell.userservice.model.impl;

import org.denevell.userservice.LoginAuthKeysSingleton;
import org.denevell.userservice.model.entities.UserEntity;
import org.denevell.userservice.model.interfaces.UserLogoutModel;

public class UserLogoutModelImpl implements UserLogoutModel {

	private LoginAuthKeysSingleton mAuthDataGenerator = LoginAuthKeysSingleton.getInstance();
	
	@Override
	public int logout(String authKey) {
		if(authKey!=null && authKey.trim().length()!=0) {
			UserEntity removed = mAuthDataGenerator.remove(authKey);
			if(removed==null) {
				return UserLogoutModel.FAIL;
			}
			UserEntity username = mAuthDataGenerator.retrieveUserEntity(authKey);
			if(username == null) {
				return UserLogoutModel.SUCCESS;
			} 
			return UserLogoutModel.FAIL;
		}
		return UserLogoutModel.FAIL;
	}

}
