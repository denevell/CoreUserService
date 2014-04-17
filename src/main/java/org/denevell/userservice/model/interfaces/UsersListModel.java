package org.denevell.userservice.model.interfaces;

import java.util.List;

import org.denevell.userservice.model.entities.UserEntity;

public interface UsersListModel {

	List<UserEntity> list(int start, int limit);

}
