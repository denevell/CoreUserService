package org.denevell.userservice.serv;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import org.denevell.userservice.model.entities.UserEntity;
import org.denevell.userservice.model.interfaces.UserGetLoggedInModel;
import org.denevell.userservice.model.interfaces.UserPasswordResetDeleteModel;
import org.denevell.userservice.model.interfaces.UserPasswordResetRequestModel;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;


@Path("user/password_reset")
public class PasswordResetRequest {
	
	@Context HttpServletResponse mResponse;
    @Inject UserPasswordResetRequestModel mUserModelRequest;
    @Inject UserPasswordResetDeleteModel mUserModelDelete;
	@Inject UserGetLoggedInModel mUserLogggedInModel;
	
	public PasswordResetRequest() {
	}
	
	@POST
	@Path("/{recoveryEmail}")
	public void requestReset(@PathParam("recoveryEmail") @NotEmpty @NotBlank String recoveryEmail) throws IOException {
		int result = mUserModelRequest.requestReset(recoveryEmail);
	    if(result==UserPasswordResetRequestModel.EMAIL_NOT_FOUND) {
	    	mResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
	    	return;
	    } 
	}	

	@DELETE
	@Path("remove/{username}")
	public void requestNoReset(
			@PathParam("username") @NotEmpty @NotBlank String username,
			@HeaderParam("AuthKey") String authKey) throws IOException {
		UserEntity userEntity = mUserLogggedInModel.get(authKey);
		if(!userEntity.isAdmin()) {
			mResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		int result = mUserModelDelete.deleteRequest(username);
		if(result==UserPasswordResetDeleteModel.CANT_FIND) {
			mResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
	}	
	
}
