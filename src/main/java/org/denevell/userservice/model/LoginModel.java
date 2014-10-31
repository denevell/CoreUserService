package org.denevell.userservice.model;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.denevell.jrappy.Jrappy;
import org.denevell.userservice.LoginAuthKeysSingleton;
import org.denevell.userservice.PasswordSaltUtils;
import org.jvnet.hk2.annotations.Service;

public interface LoginModel {

  public static class UserEntityAndAuthKey {
    public UserEntityAndAuthKey(UserEntity userEntity, String authKey) {
      this.authKey = authKey;
      this.userEntity = userEntity;
    }

    public String authKey;
    public UserEntity userEntity;
  }

  /**
   * @return The auth key
   */
  UserEntityAndAuthKey login(String username, String password);

  /**
   * Use case 1:
   * 
   * If the username and password relates to a user in the database, generate an
   * AuthKey, add the AuthKey and user id to the logged-in users database, and
   * return the UserEntity and AuthKey.
   *
   * Use case 2:
   * 
   * If the username doesn't exist in the database, or the password does not
   * relate to the password in the database, return null.
   * 
   * Use case 3:
   * 
   * User is found, but adding to logged in user database throws an exception,
   * then the exception is logged and we continue on as a successful login (the
   * user will still be added to the in-memory logged in users).
   */
  @Service
  public static class UserLoginModelImpl implements LoginModel {

    private Jrappy<UserEntity> mLoginModel = new Jrappy<UserEntity>(
        "PERSISTENCE_UNIT_NAME");
    private LoginAuthKeysSingleton mAuthDataGenerator = LoginAuthKeysSingleton
        .getInstance();
    private PasswordSaltUtils mSaltedPasswordUtils = new PasswordSaltUtils();
    @Inject
    AddLoggedInUserToPermStoreModel mLoggedInUsersPermStoreModel;

    @Override
    public UserEntityAndAuthKey login(String username, String password) {
      // Find user based on username
      UserEntity res = mLoginModel.startTransaction()
          .namedQuery(UserEntity.NAMED_QUERY_FIND_EXISTING_USERNAME)
          .queryParam("username", username).single(UserEntity.class);
      mLoginModel.commitAndCloseEntityManager();
      if (res == null) {
        return null;
      }
      // Check if password matches
      if (!mSaltedPasswordUtils
          .checkSaltedPassword(password, res.getPassword())) {
        return null;
      }
      String authKey = mAuthDataGenerator.generateAndStore(res);
      // Add user to permanent store of logged in users
      try {
        mLoggedInUsersPermStoreModel.add(authKey, res.getId());
      } catch (Exception e) {
        Logger.getLogger(getClass()).error(
            "Couldn't add user to permanent store", e);
      }

      return new UserEntityAndAuthKey(res, authKey);
    }

  }

}
