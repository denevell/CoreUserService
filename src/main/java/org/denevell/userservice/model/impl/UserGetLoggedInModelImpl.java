package org.denevell.userservice.model.impl;

import javax.inject.Inject;

import org.denevell.userservice.LoginAuthKeysSingleton;
import org.denevell.userservice.model.entities.UserEntity;
import org.denevell.userservice.model.interfaces.UserGetLoggedInModel;
import org.denevell.userservice.model.interfaces.UserGetLoggedInUserFromPermStoreModel;
import org.jvnet.hk2.annotations.Service;

/**
 * Use case 1:
 * 
 * Return a user's UserEntity if there is a record of the relating AuthKey in memory.
 * 
 * Use case 2:
 * 
 * Return a user's UserEntity if there is no static record of their AuthKey in memory 
 * but there is a record of their AuthKey in the database, and then add them to the static
 * record in memory.
 */
@Service 
public class UserGetLoggedInModelImpl implements UserGetLoggedInModel {

	private LoginAuthKeysSingleton mAuthDataGenerator = LoginAuthKeysSingleton.getInstance();
	@Inject UserGetLoggedInUserFromPermStoreModel mPermStoreModel;

	/**
	 * @return null if not found 
	 */
	@Override
	public UserEntity get(Object authKey) {
		UserEntity userEntity = mAuthDataGenerator.retrieveUserEntity((String) authKey);
		if(userEntity==null) {
			return mPermStoreModel.get(authKey);
		}
		return userEntity;
	}

}
