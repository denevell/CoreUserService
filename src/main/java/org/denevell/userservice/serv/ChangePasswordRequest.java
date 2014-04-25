package org.denevell.userservice.serv;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.denevell.natch.io.users.ChangePasswordInput;
import org.denevell.userservice.model.entities.UserEntity;
import org.denevell.userservice.model.interfaces.UserChangePasswordModel;
import org.denevell.userservice.model.interfaces.UserGetLoggedInModel;

@Path("user/password")
public class ChangePasswordRequest {
	
	@Context UriInfo info;
	@Context HttpServletResponse mResponse;
	@Context ServletContext context;
    @Inject UserChangePasswordModel mUserChangePassword;
	@Inject UserGetLoggedInModel mUserLogggedInModel;
	
	public ChangePasswordRequest() {
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void changePassword (
			@Valid final ChangePasswordInput changePass,
			@HeaderParam("AuthKey") String authKey) throws Exception {
		UserEntity userEntity = mUserLogggedInModel.get(authKey);
		if(userEntity==null) {
			mResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		int res = mUserChangePassword.changePassword(userEntity.getUsername(), changePass.getPassword());
		if(res==UserChangePasswordModel.NOT_FOUND) mResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 
	}

	@POST
	@Path("/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void changePasswordAsAdmin(
			@HeaderParam("AuthKey") String authKey,
			@PathParam("username") String username,
			@Valid final ChangePasswordInput changePass) throws Exception {
		final UserEntity userEntity = mUserLogggedInModel.get(authKey);
		if(userEntity==null || !userEntity.isAdmin()) {
			mResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		int res = mUserChangePassword.changePassword(username, changePass.getPassword());
		if(res==UserChangePasswordModel.NOT_FOUND) mResponse.sendError(HttpServletResponse.SC_NOT_FOUND); 
	}
}
