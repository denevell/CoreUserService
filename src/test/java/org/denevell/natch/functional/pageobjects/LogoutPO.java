package org.denevell.natch.functional.pageobjects;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.xml.bind.annotation.XmlRootElement;

import org.denevell.natch.functional.TestUtils;
import org.denevell.userservice.SuccessOrError;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class LogoutPO {

	private WebTarget service;

	public LogoutPO() {
		Client client = JerseyClientBuilder.createClient();
		client.register(JacksonFeature.class);
		service = client.target(TestUtils.URL_USER_SERVICE);
	}

	public LogoutResourceReturnData logout(String authKey) {
		return service
			.path("rest").path("user").path("logout").request()
			.header("AuthKey", authKey)
	    	.delete(LogoutResourceReturnData.class);		
	}
  @XmlRootElement
  public static class LogoutResourceReturnData extends SuccessOrError {

  }
}