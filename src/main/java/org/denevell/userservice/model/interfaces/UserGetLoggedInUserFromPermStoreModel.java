package org.denevell.userservice.model.interfaces;

import org.denevell.userservice.model.entities.UserEntity;

public interface UserGetLoggedInUserFromPermStoreModel {

	UserEntity get(Object authObject);

}
