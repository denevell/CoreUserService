package org.denevell.userservice.model;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.denevell.userservice.LoggedInEntity;
import org.denevell.userservice.LoginClearContextListener;
import org.jvnet.hk2.annotations.Service;

public interface RemoveLoggedInUserFromPermStoreModel {

  void remove(Object userId);

  /**
   * User case 1:
   * 
   * Gets the logged in user from the database by the auth key. Then removes the
   * entity.
   *
   */
  @Service
  @Singleton
  public static class UserRemoveLoggedInUserFromPermStoreModelImpl implements
      RemoveLoggedInUserFromPermStoreModel {

    @Override
    public void remove(Object authKey) {
      EntityManager entityManager = LoginClearContextListener.sEntityManager.createEntityManager();
      EntityTransaction transaction = entityManager.getTransaction();
      try {
      transaction.begin();
      LoggedInEntity instance = entityManager.find(LoggedInEntity.class, authKey);
      entityManager.remove(instance);
      } finally {
      transaction.commit();
      entityManager.clear();
      entityManager.close();
      }
    }

  }

}
