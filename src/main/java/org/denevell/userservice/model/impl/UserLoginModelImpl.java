package org.denevell.userservice.model.impl;

import javax.inject.Singleton;

import org.denevell.jrappy.Jrappy;
import org.denevell.userservice.LoginAuthKeysSingleton;
import org.denevell.userservice.PasswordSaltUtils;
import org.denevell.userservice.model.entities.UserEntity;
import org.denevell.userservice.model.interfaces.UserLoginModel;
import org.jvnet.hk2.annotations.Service;

@Service @Singleton
public class UserLoginModelImpl implements UserLoginModel {

	private Jrappy<UserEntity> mLoginModel = new Jrappy<UserEntity>("PERSISTENCE_UNIT_NAME");
	private LoginAuthKeysSingleton mAuthDataGenerator = LoginAuthKeysSingleton.getInstance();
	private PasswordSaltUtils mSaltedPasswordUtils = new PasswordSaltUtils();
	
	@Override
	public UserEntityAndAuthKey login(String username, String password) {
		UserEntity res = mLoginModel
				.startTransaction()
				.namedQuery(UserEntity.NAMED_QUERY_FIND_EXISTING_USERNAME)
				.queryParam("username", username)
				.single(UserEntity.class);
		mLoginModel.commitAndCloseEntityManager();
		if(res==null) {
            return null;
		}
		if (mSaltedPasswordUtils.checkSaltedPassword(password, res.getPassword())) {
			return new UserEntityAndAuthKey(res, mAuthDataGenerator.generate(res));
		} else {
            return null;
		}
	}

}
