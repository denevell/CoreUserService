package org.denevell.userservice.serv;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.denevell.natch.io.users.LogoutResourceReturnData;
import org.denevell.userservice.model.interfaces.UserLogoutModel;

@Path("user/logout")
public class LogoutRequest {
	
	@Context UriInfo info;
	@Context HttpServletResponse mResponse;
	@Inject UserLogoutModel mUserLogoutModel;
	
	public LogoutRequest() {
	}
	
	/**
	 * For DI testing.
	 */
	public LogoutRequest(UserLogoutModel model, HttpServletResponse response) {
		mUserLogoutModel = model;
		mResponse = response;
	}
		
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public LogoutResourceReturnData logout(@HeaderParam("AuthKey") String authKey) throws Exception {
		LogoutResourceReturnData returnResult = new LogoutResourceReturnData();
		int logout = mUserLogoutModel.logout(authKey);
		if(logout==UserLogoutModel.SUCCESS) {
			returnResult.setSuccessful(true);
		} else {
			returnResult.setSuccessful(false);
			mResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return returnResult;
		}
		return returnResult;
	}	

}
