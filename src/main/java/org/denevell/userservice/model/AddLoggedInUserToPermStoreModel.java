package org.denevell.userservice.model;

import org.denevell.userservice.Jrappy;
import org.denevell.userservice.LoggedInEntity;
import org.denevell.userservice.LoginClearContextListener;
import org.jvnet.hk2.annotations.Service;

public interface AddLoggedInUserToPermStoreModel {

  void add(Object authObject, long userId);

  /**
   * User case 1:
   * 
   * The AuthKey and UserId are added to the database, and deleted first if
   * already exists
   */
  @Service
  public static class UserAddLoggedInUserToPermStoreModelImpl implements
      AddLoggedInUserToPermStoreModel {

    private Jrappy<LoggedInEntity> mModel = new Jrappy<LoggedInEntity>(LoginClearContextListener.sEntityManager);

    @Override
    public void add(Object authObject, long userId) {
      try {
      LoggedInEntity instance = new LoggedInEntity();
      instance.setAuthKey((String) authObject);
      instance.setUserId(userId);
      mModel.startTransaction()
          .namedQuery(LoggedInEntity.NAMED_QUERY_FIND_BY_AUTH_KEY)
          .queryParam("auth_key", instance.getAuthKey())
          .addAndDeleteIfExistsPreviously(instance, LoggedInEntity.class);
      } finally {
      mModel.commitAndCloseEntityManager();
      }
    }

  }

}