package org.denevell.natch.functional.pageobjects;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import org.denevell.natch.functional.TestUtils;
import org.denevell.userservice.SuccessOrError;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.hibernate.validator.constraints.NotBlank;

public class LoginPO {
	
	private WebTarget mService;

	public LoginPO() {
		Client client = JerseyClientBuilder.createClient();
		client.register(JacksonFeature.class);
		mService = client.target(TestUtils.URL_USER_SERVICE);
	}

	public LoginResourceReturnData login(String username, String password) {
		LoginResourceInput loginInput = new LoginResourceInput(username, password);
		LoginResourceReturnData loginResult = mService 
	    		.path("rest").path("user").path("login").request()
	    		.post(Entity.entity(loginInput, MediaType.APPLICATION_JSON), 
	    				LoginResourceReturnData.class);
		return loginResult;
	}	

  @XmlRootElement
  public static class LoginResourceInput {

    private @NotBlank String username;
    private @NotBlank String password;

    public LoginResourceInput(String username, String password) {
      this.username = username;
      this.password = password;
    }

    public LoginResourceInput() {
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }

  @XmlRootElement
  public static class LoginResourceReturnData extends SuccessOrError {
    private String authKey = "";
    private boolean admin;

    public String getAuthKey() {
      return this.authKey;
    }

    public void setAuthKey(String authKey) {
      this.authKey = authKey;
    }

    public boolean isAdmin() {
      return admin;
    }

    public void setAdmin(boolean admin) {
      this.admin = admin;
    }

  }
	

}
