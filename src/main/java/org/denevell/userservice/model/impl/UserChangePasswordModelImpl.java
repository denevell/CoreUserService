package org.denevell.userservice.model.impl;

import org.denevell.jrappy.Jrappy;
import org.denevell.jrappy.Jrappy.RunnableWith;
import org.denevell.userservice.LoginClearContextListener;
import org.denevell.userservice.model.entities.UserEntity;
import org.denevell.userservice.model.interfaces.UserChangePasswordModel;

public class UserChangePasswordModelImpl implements UserChangePasswordModel {
	private Jrappy<UserEntity> mModel = new Jrappy<UserEntity>(LoginClearContextListener.sEntityManager);
	
	@Override
	public int changePassword(
			final String username, 
			final String password) {
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
				}, 
				UserEntity.class);
		if(found) {
			return UserChangePasswordModel.CHANGED;
		} else {
			return UserChangePasswordModel.NOT_FOUND;
		}
	    
	  } finally {
		mModel.commitAndCloseEntityManager();
	  }
	}

}
