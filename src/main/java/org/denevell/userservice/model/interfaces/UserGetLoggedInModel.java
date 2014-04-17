package org.denevell.userservice.model.interfaces;

import org.denevell.userservice.model.entities.UserEntity;

public interface UserGetLoggedInModel {

	UserEntity get(Object authObject);

}
