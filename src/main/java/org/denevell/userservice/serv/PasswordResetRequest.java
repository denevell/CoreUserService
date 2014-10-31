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

import org.denevell.userservice.model.PasswordResetDeleteModel;
import org.denevell.userservice.model.PasswordResetRequestModel;
import org.denevell.userservice.model.UserEntity;
import org.denevell.userservice.model.UserLoggedInModel;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;


@Path("user/password_reset")
public class PasswordResetRequest {
	
	@Context HttpServletResponse mResponse;
  @Inject PasswordResetRequestModel mUserModelRequest;
  @Inject PasswordResetDeleteModel mUserModelDelete;
	@Inject UserLoggedInModel mUserLogggedInModel;
	
	@POST
	@Path("/{recoveryEmail}")
	public void requestReset(@PathParam("recoveryEmail") @NotEmpty @NotBlank String recoveryEmail) throws IOException {
		int result = mUserModelRequest.requestReset(recoveryEmail);
	    if(result==PasswordResetRequestModel.EMAIL_NOT_FOUND) {
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
		if(result==PasswordResetDeleteModel.CANT_FIND) {
			mResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
	}	
	
}
