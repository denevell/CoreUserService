package org.denevell.userservice.model.impl;

import java.util.List;

import org.denevell.jrappy.Jrappy;
import org.denevell.userservice.model.entities.UserEntity;
import org.denevell.userservice.model.interfaces.UsersListModel;

public class UsersListModelImpl implements UsersListModel {

	private Jrappy<UserEntity> mModel = new Jrappy<UserEntity>("PERSISTENCE_UNIT_NAME");
	
	@Override
	public List<UserEntity> list(int start, int limit) {
		List<UserEntity> usersFromDb = mModel
				.startTransaction()
				.namedQuery(UserEntity.NAMED_QUERY_LIST_USERS).list(
						UserEntity.class);
		mModel.commitAndCloseEntityManager();
		return usersFromDb;
	}

}
