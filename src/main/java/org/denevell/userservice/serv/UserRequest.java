package org.denevell.userservice.serv;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.denevell.natch.io.users.User;
import org.denevell.userservice.model.model.UserEntity;
import org.denevell.userservice.model.model.UserLoggedInModel;

@Path("user/get")
public class UserRequest {

	@Context HttpServletResponse mResponse;
	@Inject UserLoggedInModel mUserLogggedInModel;

	public UserRequest() {
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User listUsers(@HeaderParam("AuthKey") String authKey) throws IOException {
		UserEntity userEntity = mUserLogggedInModel.get(authKey);
		if(userEntity==null) {
			mResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		User u = new User(userEntity.getUsername(), userEntity.isAdmin());
		u.setResetPasswordRequest(userEntity.isPasswordResetRequest());
		u.setRecoveryEmail(userEntity.getRecoveryEmail());
		return u;
	}

}
