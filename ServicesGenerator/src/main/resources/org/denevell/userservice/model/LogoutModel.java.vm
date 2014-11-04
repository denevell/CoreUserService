package org.denevell.userservice.model;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.log4j.Logger;
import org.denevell.userservice.LoginAuthKeysSingleton;
import org.denevell.userservice.UserEntity;
import org.jvnet.hk2.annotations.Service;

public interface LogoutModel {

  public static final int SUCCESS = 0;
  public static final int FAIL = 1;

  int logout(String authKey);

  /**
   * Use case 3:
   * 
   * Get user to logout, attempt to remove from permanent store, and if unable
   * to remove from permanent store, return a 'success', but log the error.
   *
   */
  @Service
  @Singleton
  public static class UserLogoutModelImpl implements LogoutModel {

    private LoginAuthKeysSingleton mAuthDataGenerator = LoginAuthKeysSingleton
        .getInstance();
    @Inject
    RemoveLoggedInUserFromPermStoreModel mPermLoggedInUsersModel;

    @Override
    public int logout(String authKey) {
      if (authKey != null && authKey.trim().length() != 0) {
        UserEntity removed = mAuthDataGenerator.remove(authKey);
        if (removed == null) {
          return LogoutModel.FAIL;
        }
        UserEntity username = mAuthDataGenerator.retrieveUserEntity(authKey);
        if (username == null) {
          try {
            mPermLoggedInUsersModel.remove(authKey);
          } catch (Exception e) {
            Logger
                .getLogger(getClass())
                .error(
                    "Couldn't remove the logged in user from the permanent database.",
                    e);
          }
          return LogoutModel.SUCCESS;
        }
        return LogoutModel.FAIL;
      }
      return LogoutModel.FAIL;
    }

  }

}
