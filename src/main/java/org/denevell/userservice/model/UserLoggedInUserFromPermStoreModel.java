package org.denevell.userservice.model;

import org.denevell.userservice.LoginClearContextListener;
import org.jvnet.hk2.annotations.Service;

public interface UserLoggedInUserFromPermStoreModel {

  UserEntity get(Object authObject);

  @Service
  public static class UserGetLoggedInUserFromPermStoreModelImpl implements UserLoggedInUserFromPermStoreModel {

    private Jrappy<LoggedInEntity> mModel = new Jrappy<LoggedInEntity>(LoginClearContextListener.sEntityManager);
    private Jrappy<UserEntity> mUserModel = new Jrappy<UserEntity>(LoginClearContextListener.sEntityManager);

    @Override
    public UserEntity get(Object authObject) {
      try {
      LoggedInEntity userId = mModel.startTransaction().find(authObject, false, LoggedInEntity.class);
      if (userId != null) {
        UserEntity res = mUserModel
            .useTransaction(mModel.getEntityManager())
            .find(userId.getUserId(), false, UserEntity.class);
        return res;
      } else {
        return null;
      }
      } finally {
        mUserModel.commitAndCloseEntityManager();
      }
    }

  }

}
