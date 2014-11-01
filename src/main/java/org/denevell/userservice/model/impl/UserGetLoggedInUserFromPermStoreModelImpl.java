package org.denevell.userservice.model.impl;

import org.denevell.jrappy.Jrappy;
import org.denevell.userservice.LoginClearContextListener;
import org.denevell.userservice.model.entities.UserEntity;
import org.denevell.userservice.model.entities.UserLoggedInEntity;
import org.denevell.userservice.model.interfaces.UserGetLoggedInUserFromPermStoreModel;
import org.jvnet.hk2.annotations.Service;

@Service
public class UserGetLoggedInUserFromPermStoreModelImpl implements UserGetLoggedInUserFromPermStoreModel {
	
	private Jrappy<UserLoggedInEntity> mModel = new Jrappy<UserLoggedInEntity>(LoginClearContextListener.sEntityManager);
	private Jrappy<UserEntity> mUserModel = new Jrappy<UserEntity>(LoginClearContextListener.sEntityManager);

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
