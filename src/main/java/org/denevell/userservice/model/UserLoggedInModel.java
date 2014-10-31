package org.denevell.userservice.model;

import javax.inject.Inject;

import org.denevell.userservice.LoginAuthKeysSingleton;
import org.jvnet.hk2.annotations.Service;

public interface UserLoggedInModel {

  UserEntity get(Object authObject);

  /**
   * Use case 1:
   * 
   * Return a user's UserEntity if there is a record of the relating AuthKey in
   * memory.
   * 
   * Use case 2:
   * 
   * Return a user's UserEntity if there is no static record of their AuthKey in
   * memory but there is a record of their AuthKey in the database, and then add
   * them to the static record in memory.
   */
  @Service
  public static class UserGetLoggedInModelImpl implements UserLoggedInModel {

    private LoginAuthKeysSingleton mAuthDataGenerator = LoginAuthKeysSingleton
        .getInstance();
    @Inject
    UserLoggedInUserFromPermStoreModel mPermStoreModel;

    /**
     * @return null if not found
     */
    @Override
    public UserEntity get(Object authKey) {
      UserEntity userEntity = mAuthDataGenerator
          .retrieveUserEntity((String) authKey);
      if (userEntity == null) {
        return mPermStoreModel.get(authKey);
      }
      return userEntity;
    }

  }

}
