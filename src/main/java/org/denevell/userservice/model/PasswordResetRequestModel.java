package org.denevell.userservice.model;

import org.denevell.userservice.Jrappy;
import org.denevell.userservice.LoginClearContextListener;
import org.denevell.userservice.UserEntity;


public interface PasswordResetRequestModel {
  public static final int REQUESTED = 0;
  public static final int EMAIL_NOT_FOUND = 1;

  int requestReset(String recoveryEmail);

  public static class UserPasswordResetRequestModelImpl implements PasswordResetRequestModel {

    Jrappy<UserEntity> mModel = new Jrappy<UserEntity>(LoginClearContextListener.sEntityManager);

    @Override
    public int requestReset(String recoveryEmail) {
      try {
      UserEntity user = mModel
          .namedQuery(UserEntity.NAMED_QUERY_FIND_BY_RECOVERY_EMAIL)
          .startTransaction().queryParam("recoveryEmail", recoveryEmail)
          .single(UserEntity.class);
      if (user == null) {
        return PasswordResetRequestModel.EMAIL_NOT_FOUND;
      }
      user.setPasswordResetRequest(true);
      mModel.useTransaction(mModel.getEntityManager()).update(user);
      return PasswordResetRequestModel.REQUESTED;
      } finally {
      mModel.commitAndCloseEntityManager();
      }
    }

  }

}