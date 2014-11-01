package org.denevell.userservice.model;

import org.denevell.userservice.Jrappy;
import org.denevell.userservice.LoginClearContextListener;
import org.denevell.userservice.UserEntity;
import org.denevell.userservice.Jrappy.RunnableWith;

public interface PasswordChangeModel {

  public final static int NOT_FOUND = 1;
  public final static int CHANGED = 0;

  int changePassword(String username, String password);

  public static class UserChangePasswordModelImpl implements PasswordChangeModel {
    private Jrappy<UserEntity> mModel = new Jrappy<UserEntity>(
        LoginClearContextListener.sEntityManager);

    @Override
    public int changePassword(final String username, final String password) {
      try {
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
      if (found) {
        return PasswordChangeModel.CHANGED;
      } else {
        return PasswordChangeModel.NOT_FOUND;
      }
      } finally {
      mModel.commitAndCloseEntityManager();
      }
    }

  }

}
