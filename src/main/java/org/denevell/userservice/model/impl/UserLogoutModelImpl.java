package org.denevell.userservice.model.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.log4j.Logger;
import org.denevell.userservice.LoginAuthKeysSingleton;
import org.denevell.userservice.model.entities.UserEntity;
import org.denevell.userservice.model.interfaces.UserLogoutModel;
import org.denevell.userservice.model.interfaces.UserRemoveLoggedInUserFromPermStoreModel;
import org.jvnet.hk2.annotations.Service;

/**
 * Use case 3:
 * 
 * Get user to logout, attempt to remove from permanent store, and if unable
 * to remove from permanent store, return a 'success', but log the error.
 *
 */
@Service @Singleton
public class UserLogoutModelImpl implements UserLogoutModel {

	private LoginAuthKeysSingleton mAuthDataGenerator = LoginAuthKeysSingleton.getInstance();
	@Inject UserRemoveLoggedInUserFromPermStoreModel mPermLoggedInUsersModel;
	
	@Override
	public int logout(String authKey) {
		if(authKey!=null && authKey.trim().length()!=0) {
			UserEntity removed = mAuthDataGenerator.remove(authKey);
			if(removed==null) {
				return UserLogoutModel.FAIL;
			}
			UserEntity username = mAuthDataGenerator.retrieveUserEntity(authKey);
			if(username == null) {
				try {
					mPermLoggedInUsersModel.remove(authKey);
				} catch (Exception e) {
					Logger.getLogger(getClass()).error("Couldn't remove the logged in user from the permanent database.", e);
				}
				return UserLogoutModel.SUCCESS;
			} 
			return UserLogoutModel.FAIL;
		}
		return UserLogoutModel.FAIL;
	}

}
