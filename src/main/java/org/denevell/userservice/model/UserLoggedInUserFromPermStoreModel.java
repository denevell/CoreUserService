package org.denevell.userservice.model;

import org.jvnet.hk2.annotations.Service;

public interface UserLoggedInUserFromPermStoreModel {

  UserEntity get(Object authObject);

  @Service
  public static class UserGetLoggedInUserFromPermStoreModelImpl implements UserLoggedInUserFromPermStoreModel {

    private Jrappy<LoggedInEntity> mModel = new Jrappy<LoggedInEntity>( "PERSISTENCE_UNIT_NAME");
    private Jrappy<UserEntity> mUserModel = new Jrappy<UserEntity>( "PERSISTENCE_UNIT_NAME");

    @Override
    public UserEntity get(Object authObject) {
      LoggedInEntity userId = mModel.startTransaction().find(authObject, false, LoggedInEntity.class);
      if (userId != null) {
        UserEntity res = mUserModel
            .useTransaction(mModel.getEntityManager())
            .find(userId.getUserId(), false, UserEntity.class);
        mUserModel.commitAndCloseEntityManager();
        return res;
      } else {
        return null;
      }
    }

  }

}
