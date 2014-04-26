package org.denevell.userservice.model.impl;

import org.denevell.jrappy.Jrappy;
import org.denevell.userservice.model.entities.UserLoggedInEntity;
import org.denevell.userservice.model.interfaces.UserAddLoggedInUserToPermStoreModel;
import org.jvnet.hk2.annotations.Service;

/**
 * User case 1:
 * 
 * The AuthKey and UserId are added to the database, and deleted first if already exists
 */
@Service
public class UserAddLoggedInUserToPermStoreModelImpl implements UserAddLoggedInUserToPermStoreModel {
	
	private Jrappy<UserLoggedInEntity> mModel = new Jrappy<UserLoggedInEntity>("PERSISTENCE_UNIT_NAME");

	@Override
	public void add(Object authObject, long userId) {
		UserLoggedInEntity instance = new UserLoggedInEntity();
		instance.setAuthKey((String)authObject);
		instance.setUserId(userId);
		mModel
			.startTransaction()
			.namedQuery(UserLoggedInEntity.NAMED_QUERY_FIND_BY_AUTH_KEY)
			.queryParam("auth_key", instance.getAuthKey())
			.addAndDeleteIfExistsPreviously(instance, UserLoggedInEntity.class);
		mModel.commitAndCloseEntityManager();
	}

}
