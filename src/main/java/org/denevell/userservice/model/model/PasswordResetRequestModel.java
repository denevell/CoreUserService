package org.denevell.userservice.model.model;

import org.denevell.jrappy.Jrappy;

public interface PasswordResetRequestModel {
  public static final int REQUESTED = 0;
  public static final int EMAIL_NOT_FOUND = 1;

  int requestReset(String recoveryEmail);

  public static class UserPasswordResetRequestModelImpl implements PasswordResetRequestModel {

    Jrappy<UserEntity> mModel = new Jrappy<UserEntity>("PERSISTENCE_UNIT_NAME");

    @Override
    public int requestReset(String recoveryEmail) {
      UserEntity user = mModel
          .namedQuery(UserEntity.NAMED_QUERY_FIND_BY_RECOVERY_EMAIL)
          .startTransaction().queryParam("recoveryEmail", recoveryEmail)
          .single(UserEntity.class);
      if (user == null) {
        return PasswordResetRequestModel.EMAIL_NOT_FOUND;
      }
      user.setPasswordResetRequest(true);
      mModel.useTransaction(mModel.getEntityManager()).update(user)
          .commitAndCloseEntityManager();
      return PasswordResetRequestModel.REQUESTED;
    }

  }

}
