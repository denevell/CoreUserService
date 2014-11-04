package org.denevell.natch.functional.pageobjects;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import org.denevell.natch.functional.TestUtils;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

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

	@XmlRootElement
	public static class ChangePasswordInput {
	  @NotEmpty @NotBlank private String password;

	  public String getPassword() {
	    return password;
	  }

	  public void setPassword(String password) {
	    this.password = password;
	  }
	}

}