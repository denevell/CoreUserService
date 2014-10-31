package org.denevell.userservice.model;

import org.denevell.userservice.model.Jrappy.RunnableWith;
import org.denevell.userservice.LoginAuthKeysSingleton;

public interface AdminToggleModel {

  public static int CANT_FIND = 1;
  public static int TOGGLED = 0;

  public int toggleAdmin(final String userId);

  public static class UserAdminToggleModelImpl implements AdminToggleModel {

    private Jrappy<UserEntity> mModel = new Jrappy<UserEntity>(
        "PERSISTENCE_UNIT_NAME");
    private LoginAuthKeysSingleton mAuthDataGenerator = LoginAuthKeysSingleton
        .getInstance();

    @Override
    public int toggleAdmin(final String userId) {
      boolean found = mModel
          .startTransaction()
          .queryParam("username", userId)
          .findAndUpdate(UserEntity.NAMED_QUERY_FIND_EXISTING_USERNAME,
              new RunnableWith<UserEntity>() {
                @Override
                public void item(UserEntity item) {
                  boolean admin = !item.isAdmin();
                  item.setAdmin(admin);
                  UserEntity loggedInEntity = mAuthDataGenerator
                      .getLoggedinUser(userId);
                  if (loggedInEntity != null) {
                    loggedInEntity.setAdmin(admin);
                  }
                }
              }, UserEntity.class);
      mModel.commitAndCloseEntityManager();
      if (found) {
        return AdminToggleModel.TOGGLED;
      } else {
        return AdminToggleModel.CANT_FIND;
      }
    }

  }
}
