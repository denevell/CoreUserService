package org.denevell.userservice.model.impl;

import org.denevell.jrappy.Jrappy;
import org.denevell.userservice.model.entities.UserEntity;
import org.denevell.userservice.model.entities.UserLoggedInEntity;
import org.denevell.userservice.model.interfaces.UserGetLoggedInUserFromPermStoreModel;
import org.jvnet.hk2.annotations.Service;

/**
 * User case 1:
 * 
 * The AuthKey and UserId are added to the database, and deleted first if already exists
 */
@Service
public class UserGetLoggedInUserFromPermStoreModelImpl implements UserGetLoggedInUserFromPermStoreModel {
	
	private Jrappy<UserLoggedInEntity> mModel = new Jrappy<UserLoggedInEntity>("PERSISTENCE_UNIT_NAME");
	private Jrappy<UserEntity> mUserModel = new Jrappy<UserEntity>("PERSISTENCE_UNIT_NAME");

	@Override
	public UserEntity get(Object authObject) {
		UserLoggedInEntity userId = mModel
			.startTransaction()
			.find(authObject, false, UserLoggedInEntity.class);
		if(userId!=null) {
			UserEntity res = mUserModel
				.useTransaction(mModel.getEntityManager())
				.find(userId.getUserId(), false, UserEntity.class);
			mUserModel.commitAndCloseEntityManager();
			return res;
		} else {
			return null;
		}
	}

}
