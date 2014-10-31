package org.denevell.userservice.model;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
      EntityManager entityManager = Persistence.createEntityManagerFactory(
          "PERSISTENCE_UNIT_NAME").createEntityManager();
      EntityTransaction transaction = entityManager.getTransaction();
      transaction.begin();
      LoggedInEntity instance = entityManager.find(
          LoggedInEntity.class, authKey);
      entityManager.remove(instance);
      transaction.commit();
      entityManager.clear();
      entityManager.close();
    }

  }

}
