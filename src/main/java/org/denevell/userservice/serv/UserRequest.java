package org.denevell.userservice.serv;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.denevell.userservice.UserEntity;
import org.denevell.userservice.model.UserLoggedInModel;

@Path("user/get")
public class UserRequest {

  @Context
  HttpServletResponse mResponse;
  @Inject
  UserLoggedInModel mUserLogggedInModel;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public User listUsers(@HeaderParam("AuthKey") String authKey)
      throws IOException {
    UserEntity userEntity = mUserLogggedInModel.get(authKey);
    if (userEntity == null) {
      mResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
      return null;
    }
    User u = new User(userEntity.getUsername(), userEntity.isAdmin());
    u.setResetPasswordRequest(userEntity.isPasswordResetRequest());
    u.setRecoveryEmail(userEntity.getRecoveryEmail());
    return u;
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
