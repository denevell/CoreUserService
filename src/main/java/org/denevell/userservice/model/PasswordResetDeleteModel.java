package org.denevell.userservice.model;

import org.denevell.jrappy.Jrappy;

public interface PasswordResetDeleteModel {

  public final static int CANT_FIND = 1;
  public final static int UPDATED = 0;

  int deleteRequest(String username);

  public static class UserPasswordResetDeleteModelImpl implements PasswordResetDeleteModel {

    private Jrappy<UserEntity> mModel = new Jrappy<UserEntity>(
        "PERSISTENCE_UNIT_NAME")
        .namedQuery(UserEntity.NAMED_QUERY_FIND_EXISTING_USERNAME);

    @Override
    public int deleteRequest(String username) {
      UserEntity user = mModel.startTransaction()
          .queryParam("username", username).single(UserEntity.class);
      if (user == null) {
        return PasswordResetDeleteModel.CANT_FIND;
      }
      user.setPasswordResetRequest(false);
      mModel.useTransaction(mModel.getEntityManager()).update(user);
      mModel.commitAndCloseEntityManager();
      return PasswordResetDeleteModel.UPDATED;
    }

  }

}
