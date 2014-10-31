package org.denevell.userservice;

import org.denevell.userservice.model.model.AddLoggedInUserToPermStoreModel;
import org.denevell.userservice.model.model.AddLoggedInUserToPermStoreModel.UserAddLoggedInUserToPermStoreModelImpl;
import org.denevell.userservice.model.model.AddModel;
import org.denevell.userservice.model.model.AddModel.UserAddModelImpl;
import org.denevell.userservice.model.model.AdminToggleModel;
import org.denevell.userservice.model.model.AdminToggleModel.UserAdminToggleModelImpl;
import org.denevell.userservice.model.model.PasswordChangeModel;
import org.denevell.userservice.model.model.PasswordChangeModel.UserChangePasswordModelImpl;
import org.denevell.userservice.model.model.UserLoggedInModel;
import org.denevell.userservice.model.model.UserLoggedInModel.UserGetLoggedInModelImpl;
import org.denevell.userservice.model.model.UserLoggedInUserFromPermStoreModel;
import org.denevell.userservice.model.model.UserLoggedInUserFromPermStoreModel.UserGetLoggedInUserFromPermStoreModelImpl;
import org.denevell.userservice.model.model.LoginModel;
import org.denevell.userservice.model.model.LoginModel.UserLoginModelImpl;
import org.denevell.userservice.model.model.LogoutModel;
import org.denevell.userservice.model.model.LogoutModel.UserLogoutModelImpl;
import org.denevell.userservice.model.model.PasswordResetDeleteModel;
import org.denevell.userservice.model.model.PasswordResetDeleteModel.UserPasswordResetDeleteModelImpl;
import org.denevell.userservice.model.model.PasswordResetRequestModel;
import org.denevell.userservice.model.model.PasswordResetRequestModel.UserPasswordResetRequestModelImpl;
import org.denevell.userservice.model.model.RemoveLoggedInUserFromPermStoreModel;
import org.denevell.userservice.model.model.RemoveLoggedInUserFromPermStoreModel.UserRemoveLoggedInUserFromPermStoreModelImpl;
import org.denevell.userservice.model.model.UsersModel;
import org.denevell.userservice.model.model.UsersModel.UsersListModelImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class DependencyInjectionBinder extends AbstractBinder {
	@Override
	protected void configure() {
		bind(UserAddModelImpl.class).to(AddModel.class);
		bind(UserAdminToggleModelImpl.class).to(AdminToggleModel.class);
		bind(UserChangePasswordModelImpl.class).to(PasswordChangeModel.class);
		bind(UserLoginModelImpl.class).to(LoginModel.class);
		bind(UserLogoutModelImpl.class).to(LogoutModel.class);
		bind(UserPasswordResetRequestModelImpl.class).to(PasswordResetRequestModel.class);
		bind(UserPasswordResetDeleteModelImpl.class).to(PasswordResetDeleteModel.class);
		bind(UsersListModelImpl.class).to(UsersModel.class);
		bind(UserGetLoggedInModelImpl.class).to(UserLoggedInModel.class);
		bind(UserAddLoggedInUserToPermStoreModelImpl.class).to(AddLoggedInUserToPermStoreModel.class);
		bind(UserGetLoggedInUserFromPermStoreModelImpl.class).to(UserLoggedInUserFromPermStoreModel.class);
		bind(UserRemoveLoggedInUserFromPermStoreModelImpl.class).to(RemoveLoggedInUserFromPermStoreModel.class);
	}
}