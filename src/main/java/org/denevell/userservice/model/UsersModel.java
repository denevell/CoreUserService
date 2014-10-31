package org.denevell.userservice.model;

import java.util.List;

public interface UsersModel {

  List<UserEntity> list(int start, int limit);

  public static class UsersListModelImpl implements UsersModel {

    private Jrappy<UserEntity> mModel = new Jrappy<UserEntity>(
        "PERSISTENCE_UNIT_NAME");

    @Override
    public List<UserEntity> list(int start, int limit) {
      List<UserEntity> usersFromDb = mModel.startTransaction()
          .namedQuery(UserEntity.NAMED_QUERY_LIST_USERS).list(UserEntity.class);
      mModel.commitAndCloseEntityManager();
      return usersFromDb;
    }

  }

}
