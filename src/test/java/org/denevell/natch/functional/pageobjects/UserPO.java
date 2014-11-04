package org.denevell.natch.functional.pageobjects;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.denevell.natch.functional.TestUtils;
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
	
	@XmlRootElement
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class User {

    private String username;
    private boolean admin;
    private boolean resetPasswordRequest;
    private String recoveryEmail;

    public User() {
    }

    public User(String username, boolean isAdmin) {
      this.username = username;
      this.admin = isAdmin;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public boolean isAdmin() {
      return admin;
    }

    public void setAdmin(boolean isAdmin) {
      this.admin = isAdmin;
    }

    public boolean isResetPasswordRequest() {
      return resetPasswordRequest;
    }

    public void setResetPasswordRequest(boolean resetPasswordRequest) {
      this.resetPasswordRequest = resetPasswordRequest;
    }

    public String getRecoveryEmail() {
      return recoveryEmail;
    }

    public void setRecoveryEmail(String recoveryEmail) {
      this.recoveryEmail = recoveryEmail;
    }

  }
	

}
