package org.denevell.userservice.serv;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.denevell.natch.io.users.LoginResourceInput;
import org.denevell.natch.io.users.LoginResourceReturnData;
import org.denevell.userservice.model.interfaces.UserLoginModel;
import org.denevell.userservice.model.interfaces.UserLoginModel.UserEntityAndAuthKey;

@Path("user/login")
public class LoginRequest {
	
	@Context HttpServletResponse mResponse;
	@Inject UserLoginModel mModel;
	
	public LoginRequest() {
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public LoginResourceReturnData login(@Valid LoginResourceInput loginInput) throws IOException {
		LoginResourceReturnData returnResult = new LoginResourceReturnData();
		UserEntityAndAuthKey res = mModel.login(loginInput.getUsername(), loginInput.getPassword());
		if(res==null) {
            mResponse.sendError(HttpServletResponse.SC_FORBIDDEN); 
            return null;
		} else {
			returnResult.setSuccessful(true);
			returnResult.setAdmin(res.userEntity.isAdmin());
			returnResult.setAuthKey(res.authKey);
			return returnResult;
		} 
	}

}
