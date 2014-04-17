package org.denevell.userservice.model.impl;

import org.denevell.jrappy.Jrappy;
import org.denevell.jrappy.Jrappy.RunnableWith;
import org.denevell.userservice.model.entities.UserEntity;
import org.denevell.userservice.model.interfaces.UserChangePasswordModel;

public class UserChangePasswordModelImpl implements UserChangePasswordModel {
	private Jrappy<UserEntity> mModel = new Jrappy<UserEntity>("PERSISTENCE_UNIT_NAME");
	
	@Override
	public int changePassword(
			final String username, 
			final String password) {
		boolean found = mModel
			.startTransaction()
			.queryParam("username", username)
			.findAndUpdate(UserEntity.NAMED_QUERY_FIND_EXISTING_USERNAME,
				new RunnableWith<UserEntity>() {
					@Override
					public void item(UserEntity item) {
						item.generatePassword(password);
					}
				}, 
				UserEntity.class);
		mModel.commitAndCloseEntityManager();
		if(found) {
			return UserChangePasswordModel.CHANGED;
		} else {
			return UserChangePasswordModel.NOT_FOUND;
		}
	}

}
