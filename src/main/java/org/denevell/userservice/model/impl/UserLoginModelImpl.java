package org.denevell.userservice.model.impl;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.denevell.jrappy.Jrappy;
import org.denevell.userservice.LoginAuthKeysSingleton;
import org.denevell.userservice.LoginClearContextListener;
import org.denevell.userservice.PasswordSaltUtils;
import org.denevell.userservice.model.entities.UserEntity;
import org.denevell.userservice.model.interfaces.UserAddLoggedInUserToPermStoreModel;
import org.denevell.userservice.model.interfaces.UserLoginModel;
import org.jvnet.hk2.annotations.Service;

/**
 * Use case 1: 
 * 
 * If the username and password relates to a user in the database, generate
 * an AuthKey, add the AuthKey and user id to the logged-in users database, 
 * and return the UserEntity and AuthKey.
 *
 * Use case 2:
 * 
 * If the username doesn't exist in the database, or the password does not relate
 * to the password in the database, return null.
 * 
 * Use case 3:
 * 
 * User is found, but adding to logged in user database throws an exception, 
 * then the exception is logged and we continue on as a successful login (the
 * user will still be added to the in-memory logged in users).
 */
@Service
public class UserLoginModelImpl implements UserLoginModel {

	private Jrappy<UserEntity> mLoginModel = new Jrappy<UserEntity>(LoginClearContextListener.sEntityManager);
	private LoginAuthKeysSingleton mAuthDataGenerator = LoginAuthKeysSingleton.getInstance();
	private PasswordSaltUtils mSaltedPasswordUtils = new PasswordSaltUtils();
	@Inject UserAddLoggedInUserToPermStoreModel mLoggedInUsersPermStoreModel;
	
	@Override
	public UserEntityAndAuthKey login(String username, String password) {
    // Find user based on username
		UserEntity res = null;
	  try {
		res = mLoginModel
				.startTransaction()
				.namedQuery(UserEntity.NAMED_QUERY_FIND_EXISTING_USERNAME)
				.queryParam("username", username)
				.single(UserEntity.class);
	  } finally {
		mLoginModel.commitAndCloseEntityManager();
	  }
		if(res==null) {
            return null;
		}
		// Check if password matches
		if (!mSaltedPasswordUtils.checkSaltedPassword(password, res.getPassword())) {
            return null;
		}
		String authKey = mAuthDataGenerator.generateAndStore(res);
		// Add user to permanent store of logged in users
		try {
			mLoggedInUsersPermStoreModel.add(authKey, res.getId());
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("Couldn't add user to permanent store", e);
		}

		return new UserEntityAndAuthKey(res, authKey);
	}

}
