package org.denevell.userservice.model;

import org.denevell.userservice.model.Jrappy.RunnableWith;

public interface PasswordChangeModel {

  public final static int NOT_FOUND = 1;
  public final static int CHANGED = 0;

  int changePassword(String username, String password);

  public static class UserChangePasswordModelImpl implements PasswordChangeModel {
    private Jrappy<UserEntity> mModel = new Jrappy<UserEntity>(
        "PERSISTENCE_UNIT_NAME");

    @Override
    public int changePassword(final String username, final String password) {
      boolean found = mModel
          .startTransaction()
          .queryParam("username", username)
          .findAndUpdate(UserEntity.NAMED_QUERY_FIND_EXISTING_USERNAME,
              new RunnableWith<UserEntity>() {
                @Override
                public void item(UserEntity item) {
                  item.generatePassword(password);
                }
              }, UserEntity.class);
      mModel.commitAndCloseEntityManager();
      if (found) {
        return PasswordChangeModel.CHANGED;
      } else {
        return PasswordChangeModel.NOT_FOUND;
      }
    }

  }

}
