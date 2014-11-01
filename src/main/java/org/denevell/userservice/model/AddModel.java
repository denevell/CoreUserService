package org.denevell.userservice.model;

import org.denevell.userservice.Jrappy;
import org.denevell.userservice.LoginClearContextListener;
import org.denevell.userservice.UserEntity;
import org.denevell.userservice.Jrappy.RunnableWith;
import org.jvnet.hk2.annotations.Service;

public interface AddModel {

  public static int ADDED = 0;
  public static int EMAIL_ALREADY_EXISTS = 1;
  public static int USER_ALREADY_EXISTS = 2;

  public int add(final UserEntity user);

  @Service
  public static class UserAddModelImpl implements AddModel {

    private Jrappy<UserEntity> mModel = new Jrappy<UserEntity>(
        LoginClearContextListener.sEntityManager);

    public int add(final UserEntity user) {
      try {
      boolean exists = mModel.startTransaction()
          .namedQuery(UserEntity.NAMED_QUERY_FIND_BY_RECOVERY_EMAIL)
          .queryParam("recoveryEmail", user.getRecoveryEmail()).exists();
      if (exists) {
        return AddModel.EMAIL_ALREADY_EXISTS;
      }
      boolean added = mModel
          .clearQueryParams()
          .useTransaction(mModel.getEntityManager())
          .ifFirstItem(UserEntity.NAMED_QUERY_COUNT,
              new RunnableWith<UserEntity>() {
                @Override
                public void item(UserEntity item) {
                  item.setAdmin(true);
                }
              })
          .queryParam("username", user.getUsername())
          .addIfDoesntExist(UserEntity.NAMED_QUERY_FIND_EXISTING_USERNAME, user);
      if (!added) {
        return AddModel.USER_ALREADY_EXISTS;
      } else {
        return AddModel.ADDED;
      }
      } finally {
      mModel.commitAndCloseEntityManager();
      }
    }

  }

}