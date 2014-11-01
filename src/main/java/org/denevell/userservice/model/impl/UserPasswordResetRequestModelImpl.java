package org.denevell.userservice.model.impl;

import org.denevell.jrappy.Jrappy;
import org.denevell.userservice.LoginClearContextListener;
import org.denevell.userservice.model.entities.UserEntity;
import org.denevell.userservice.model.interfaces.UserPasswordResetRequestModel;

public class UserPasswordResetRequestModelImpl implements UserPasswordResetRequestModel {
	
	Jrappy<UserEntity> mModel = new Jrappy<UserEntity>(LoginClearContextListener.sEntityManager);
	
	@Override
	public int requestReset(String recoveryEmail) {
	  try {
	    UserEntity user = mModel 
	    		.namedQuery(UserEntity.NAMED_QUERY_FIND_BY_RECOVERY_EMAIL)
	    		.startTransaction()
	    		.queryParam("recoveryEmail", recoveryEmail).single(UserEntity.class);  	    
	    if(user==null) {
	    	return UserPasswordResetRequestModel.EMAIL_NOT_FOUND;
	    }
		user.setPasswordResetRequest(true);
		mModel
			.useTransaction(mModel.getEntityManager())
			.update(user);
	    return UserPasswordResetRequestModel.REQUESTED;
	    
	  } finally {
			mModel.commitAndCloseEntityManager();
	  }
	}

}
