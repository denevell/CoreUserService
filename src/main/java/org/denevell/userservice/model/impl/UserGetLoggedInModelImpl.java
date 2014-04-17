package org.denevell.userservice.model.impl;

import javax.inject.Singleton;

import org.denevell.userservice.LoginAuthKeysSingleton;
import org.denevell.userservice.model.entities.UserEntity;
import org.denevell.userservice.model.interfaces.UserGetLoggedInModel;
import org.jvnet.hk2.annotations.Service;

@Service @Singleton
public class UserGetLoggedInModelImpl implements UserGetLoggedInModel {

	private LoginAuthKeysSingleton mAuthDataGenerator = LoginAuthKeysSingleton.getInstance();

	@Override
	public UserEntity get(Object authKey) {
		UserEntity userEntity = mAuthDataGenerator.retrieveUserEntity((String) authKey);
		return userEntity;
	}

}
