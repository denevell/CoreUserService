package org.denevell.userservice.serv;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.denevell.natch.io.users.RegisterResourceInput;
import org.denevell.natch.io.users.RegisterResourceReturnData;
import org.denevell.userservice.model.entities.UserEntity;
import org.denevell.userservice.model.interfaces.UserAddModel;


@Path("user")
public class RegisterRequest {
	
	@Context UriInfo info;
	@Context ServletContext context;
    @Inject UserAddModel mUserAddModel;
	
	public RegisterRequest() {
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public RegisterResourceReturnData register(@Valid RegisterResourceInput registerInput) {
		RegisterResourceReturnData regReturnData = new RegisterResourceReturnData();
		UserEntity u = new UserEntity(registerInput);
		int added = mUserAddModel.add(u);
		if(added==UserAddModel.EMAIL_ALREADY_EXISTS) {
			regReturnData.setSuccessful(false);
			regReturnData.setError("Email already exists");
			return regReturnData;
		} else if (added==UserAddModel.USER_ALREADY_EXISTS) {
			regReturnData.setSuccessful(false);
			regReturnData.setError("Username already exists");
		} else {
			regReturnData.setSuccessful(true);
		}
		return regReturnData;
	}	
	
}
