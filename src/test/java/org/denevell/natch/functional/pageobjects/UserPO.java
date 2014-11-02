package org.denevell.natch.functional.pageobjects;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import org.denevell.natch.functional.TestUtils;
import org.denevell.userservice.serv.UserRequest.User;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class UserPO {
	
	private WebTarget mService;

	public UserPO() {
		Client client = JerseyClientBuilder.createClient();
		client.register(JacksonFeature.class);
		mService = client.target(TestUtils.URL_USER_SERVICE);
	}

	public User user(String authKey) {
		User ret = mService 
	    		.path("rest").path("user").path("get").request()
	    		.header("AuthKey", authKey)
	    		.get(User.class);
		return ret;
	}	
	

}
