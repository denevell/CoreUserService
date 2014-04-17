package org.denevell.userservice.serv;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.denevell.natch.io.base.SuccessOrError;
import org.denevell.userservice.model.entities.UserEntity;
import org.denevell.userservice.model.interfaces.UserAdminToggleModel;
import org.denevell.userservice.model.interfaces.UserGetLoggedInModel;


@Path("user/admin/toggle")
public class UsersAdminToggleRequest {
	
	@Context HttpServletResponse mResponse;
	@Inject UserAdminToggleModel mModel;
	@Inject UserGetLoggedInModel mUserLogggedInModel;
	
	public UsersAdminToggleRequest() {
	}
	
	@POST
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public SuccessOrError toggleAdmin(
			@PathParam("userId") final String userId,
			@HeaderParam("AuthKey") String authKey) throws IOException {
		UserEntity userEntity = mUserLogggedInModel.get(authKey);
		if (!userEntity.isAdmin()) {
			mResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		} else {
			int result = mModel.toggleAdmin(userId);
			if(result==UserAdminToggleModel.CANT_FIND) {
				mResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			} else {
				SuccessOrError successOrError = new SuccessOrError();
				successOrError.setSuccessful(true);
				return successOrError;
			}
		}
	}
}
