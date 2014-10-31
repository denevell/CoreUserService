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
import javax.xml.bind.annotation.XmlRootElement;

import org.denevell.userservice.model.AddModel;
import org.denevell.userservice.model.UserEntity;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Path("user")
public class RegisterRequest {

  @Context
  UriInfo info;
  @Context
  ServletContext context;
  @Inject
  AddModel mUserAddModel;

  public RegisterRequest() {
  }

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public RegisterResourceReturnData register(
      @Valid RegisterResourceInput registerInput) {
    RegisterResourceReturnData regReturnData = new RegisterResourceReturnData();
    UserEntity u = new UserEntity(registerInput);
    int added = mUserAddModel.add(u);
    if (added == AddModel.EMAIL_ALREADY_EXISTS) {
      regReturnData.setSuccessful(false);
      regReturnData.setError("Email already exists");
      return regReturnData;
    } else if (added == AddModel.USER_ALREADY_EXISTS) {
      regReturnData.setSuccessful(false);
      regReturnData.setError("Username already exists");
    } else {
      regReturnData.setSuccessful(true);
    }
    return regReturnData;
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
