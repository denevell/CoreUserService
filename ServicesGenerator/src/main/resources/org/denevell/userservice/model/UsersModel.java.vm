package org.denevell.userservice.model;

import java.util.List;

import org.denevell.userservice.Jrappy;
import org.denevell.userservice.LoginClearContextListener;
import org.denevell.userservice.UserEntity;

public interface UsersModel {

  List<UserEntity> list(int start, int limit);

  public static class UsersListModelImpl implements UsersModel {

    private Jrappy<UserEntity> mModel = new Jrappy<UserEntity>(LoginClearContextListener.sEntityManager);

    @Override
    public List<UserEntity> list(int start, int limit) {
      try {
      List<UserEntity> usersFromDb = mModel.startTransaction()
          .namedQuery(UserEntity.NAMED_QUERY_LIST_USERS).list(UserEntity.class);
      return usersFromDb;
      } finally {
      mModel.commitAndCloseEntityManager();
      }
    }

  }

}
