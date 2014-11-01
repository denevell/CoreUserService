package org.denevell.userservice.model.impl;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
		//EntityManagerFactory entityManager2 = Persistence.createEntityManagerFactory("PERSISTENCE_UNIT_NAME");
    EntityManager entityManager = LoginClearContextListener.sEntityManager.createEntityManager();//entityManager2.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		try {
		  transaction.begin();
		  UserLoggedInEntity instance = entityManager.find(UserLoggedInEntity.class, authKey);		
		  entityManager.remove(instance);
		} finally {
		  transaction.commit();
		  entityManager.clear();
		  entityManager.close();
		  //entityManager2.close();
		}
	}

}
