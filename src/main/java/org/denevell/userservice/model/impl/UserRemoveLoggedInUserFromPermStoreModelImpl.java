package org.denevell.userservice.model.impl;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.denevell.userservice.LoginClearContextListener;
import org.denevell.userservice.model.entities.UserLoggedInEntity;
import org.denevell.userservice.model.interfaces.UserRemoveLoggedInUserFromPermStoreModel;
import org.jvnet.hk2.annotations.Service;

/**
 * User case 1:
 * 
 * Gets the logged in user from the database by the auth key.
 * Then removes the entity.
 *
 */
@Service @Singleton
public class UserRemoveLoggedInUserFromPermStoreModelImpl implements UserRemoveLoggedInUserFromPermStoreModel {
	
	@Override
	public void remove(Object authKey) {
		EntityManager entityManager = LoginClearContextListener.sEntityManager.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		try {
		  transaction.begin();
		  UserLoggedInEntity instance = entityManager.find(UserLoggedInEntity.class, authKey);		
		  entityManager.remove(instance);
		} finally {
		  transaction.commit();
		  entityManager.clear();
		}
	}

}
