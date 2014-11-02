package org.denevell.natch.functional.pageobjects;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.denevell.natch.functional.TestUtils;
import org.denevell.userservice.serv.PasswordChangeRequest.ChangePasswordInput;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class PasswordChangePO {

	private WebTarget mService;

	public PasswordChangePO() {
		Client client = JerseyClientBuilder.createClient();
		client.register(JacksonFeature.class);
		mService = client.target(TestUtils.URL_USER_SERVICE);
	}

	public Response change(String string, String authKey) {
		ChangePasswordInput changePassword = new ChangePasswordInput();
		changePassword.setPassword(string);
		Response res = mService 
	    	.path("rest").path("user").path("password").request()
	    	.header("AuthKey", authKey)
	    	.post(Entity.entity(changePassword, MediaType.APPLICATION_JSON));
		return res;
	}

	public Response changeAsAdmin(String username, String pass, String authKey) {
		ChangePasswordInput changePassword = new ChangePasswordInput();
		changePassword.setPassword(pass);
		Response res = mService 
	    	.path("rest").path("user").path("password").path(username).request()
	    	.header("AuthKey", authKey)
	    	.post(Entity.entity(changePassword, MediaType.APPLICATION_JSON));
		return res;
	}

}