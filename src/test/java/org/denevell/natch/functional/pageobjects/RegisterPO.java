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
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class RegisterPO {
	
	private WebTarget mService;

	public RegisterPO() {
		Client client = JerseyClientBuilder.createClient();
		client.register(JacksonFeature.class);
		mService = client.target(TestUtils.URL_USER_SERVICE);
	}

	public RegisterResourceReturnData register(String username, String password) {
		return register(username, password, null);
	}	

	public RegisterResourceReturnData register(String username, String password, String emailRecover) {
		RegisterResourceInput registerInput = new RegisterResourceInput();
		registerInput.setUsername(username);
		registerInput.setPassword(password);
		registerInput.setRecoveryEmail(emailRecover);
		RegisterResourceReturnData result = mService 
	    		.path("rest").path("user").request()
	    		.put(Entity.entity(registerInput, MediaType.APPLICATION_JSON), 
	    				RegisterResourceReturnData.class);
		return result;
	}	

  @XmlRootElement
  public static class RegisterResourceInput {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Email(regexp = ".+@.+\\..+")
    private String recoveryEmail;

    public RegisterResourceInput(@NotBlank String username,
        @NotBlank String password) {
      this.setUsername(username);
      this.setPassword(password);
    }

    public RegisterResourceInput(@NotBlank String username,
        @NotBlank String password, @Email String recoveryEmail) {
      this.setUsername(username);
      this.setPassword(password);
      this.setRecoveryEmail(recoveryEmail);
    }

    public RegisterResourceInput() {
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

    public String getRecoveryEmail() {
      return recoveryEmail;
    }

    public void setRecoveryEmail(String recoveryEmail) {
      this.recoveryEmail = recoveryEmail;
    }
  }

  @XmlRootElement
  public static class RegisterResourceReturnData extends SuccessOrError {
  }

	

}
