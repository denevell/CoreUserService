package org.denevell.userservice.model.impl;

import org.denevell.jrappy.Jrappy;
import org.denevell.userservice.LoginClearContextListener;
import org.denevell.userservice.model.entities.UserEntity;
import org.denevell.userservice.model.interfaces.UserPasswordResetDeleteModel;

public class UserPasswordResetDeleteModelImpl implements UserPasswordResetDeleteModel {

	private Jrappy<UserEntity> mModel = new Jrappy<UserEntity>(LoginClearContextListener.sEntityManager)
		 .namedQuery(UserEntity.NAMED_QUERY_FIND_EXISTING_USERNAME);
	
	@Override
	public int deleteRequest(String username) {
	    UserEntity user = mModel
	    		.startTransaction()
	    		.queryParam("username", username)
	    		.single(UserEntity.class);  	    
	    if(user==null) {
	    	return UserPasswordResetDeleteModel.CANT_FIND;
	    }
		user.setPasswordResetRequest(false);
		mModel
			.useTransaction(mModel.getEntityManager())
			.update(user);
		mModel.commitAndCloseEntityManager();
		return UserPasswordResetDeleteModel.UPDATED;
	}

}
