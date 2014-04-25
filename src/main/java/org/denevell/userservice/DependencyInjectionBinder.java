package org.denevell.userservice;

import org.denevell.userservice.model.impl.UserAddLoggedInUserToPermStoreModelImpl;
import org.denevell.userservice.model.impl.UserAddModelImpl;
import org.denevell.userservice.model.impl.UserAdminToggleModelImpl;
import org.denevell.userservice.model.impl.UserChangePasswordModelImpl;
import org.denevell.userservice.model.impl.UserGetLoggedInModelImpl;
import org.denevell.userservice.model.impl.UserGetLoggedInUserFromPermStoreModelImpl;
import org.denevell.userservice.model.impl.UserLoginModelImpl;
import org.denevell.userservice.model.impl.UserLogoutModelImpl;
import org.denevell.userservice.model.impl.UserPasswordResetDeleteModelImpl;
import org.denevell.userservice.model.impl.UserPasswordResetRequestModelImpl;
import org.denevell.userservice.model.impl.UserRemoveLoggedInUserFromPermStoreModelImpl;
import org.denevell.userservice.model.impl.UsersListModelImpl;
import org.denevell.userservice.model.interfaces.UserAddLoggedInUserToPermStoreModel;
import org.denevell.userservice.model.interfaces.UserAddModel;
import org.denevell.userservice.model.interfaces.UserAdminToggleModel;
import org.denevell.userservice.model.interfaces.UserChangePasswordModel;
import org.denevell.userservice.model.interfaces.UserGetLoggedInModel;
import org.denevell.userservice.model.interfaces.UserGetLoggedInUserFromPermStoreModel;
import org.denevell.userservice.model.interfaces.UserLoginModel;
import org.denevell.userservice.model.interfaces.UserLogoutModel;
import org.denevell.userservice.model.interfaces.UserPasswordResetDeleteModel;
import org.denevell.userservice.model.interfaces.UserPasswordResetRequestModel;
import org.denevell.userservice.model.interfaces.UserRemoveLoggedInUserFromPermStoreModel;
import org.denevell.userservice.model.interfaces.UsersListModel;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class DependencyInjectionBinder extends AbstractBinder {
	@Override
	protected void configure() {
		bind(UserAddModelImpl.class).to(UserAddModel.class);
		bind(UserAdminToggleModelImpl.class).to(UserAdminToggleModel.class);
		bind(UserChangePasswordModelImpl.class).to(UserChangePasswordModel.class);
		bind(UserLoginModelImpl.class).to(UserLoginModel.class);
		bind(UserLogoutModelImpl.class).to(UserLogoutModel.class);
		bind(UserPasswordResetRequestModelImpl.class).to(UserPasswordResetRequestModel.class);
		bind(UserPasswordResetDeleteModelImpl.class).to(UserPasswordResetDeleteModel.class);
		bind(UsersListModelImpl.class).to(UsersListModel.class);
		bind(UserGetLoggedInModelImpl.class).to(UserGetLoggedInModel.class);
		bind(UserAddLoggedInUserToPermStoreModelImpl.class).to(UserAddLoggedInUserToPermStoreModel.class);
		bind(UserGetLoggedInUserFromPermStoreModelImpl.class).to(UserGetLoggedInUserFromPermStoreModel.class);
		bind(UserRemoveLoggedInUserFromPermStoreModelImpl.class).to(UserRemoveLoggedInUserFromPermStoreModel.class);
	}
}