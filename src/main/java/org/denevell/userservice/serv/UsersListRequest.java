package org.denevell.userservice.serv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import org.denevell.userservice.model.UserEntity;
import org.denevell.userservice.model.UserLoggedInModel;
import org.denevell.userservice.model.UsersModel;
import org.denevell.userservice.serv.UserRequest.User;
import org.hibernate.validator.constraints.NotBlank;

@Path("user/list")
public class UsersListRequest {

  @Context
  HttpServletResponse mResponse;
  @Inject
  UsersModel mUserList;
  @Inject
  UserLoggedInModel mUserLogggedInModel;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public UserList listUsers(@HeaderParam("AuthKey") @NotBlank String authKey)
      throws IOException {
    UserEntity userEntity = mUserLogggedInModel.get(authKey);
    if (!userEntity.isAdmin()) {
      mResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return null;
    } else {
      UserList usersList = new UserList();
      List<UserEntity> usersFromDb = mUserList.list(0, 1000);
      for (UserEntity user : usersFromDb) {
        User u = new User(user.getUsername(), user.isAdmin());
        u.setResetPasswordRequest(user.isPasswordResetRequest());
        u.setRecoveryEmail(user.getRecoveryEmail());
        usersList.getUsers().add(u);
      }
      return usersList;
    }
  }

  @XmlRootElement
  public static class UserList {

    private long numUsers;
    private List<User> users = new ArrayList<User>();

    public List<User> getUsers() {
      return users;
    }

    public void setUsers(List<User> posts) {
      this.users = posts;
    }

    public long getNumUsers() {
      return numUsers;
    }

    public void setNumUsers(long num) {
      this.numUsers = num;
    }

  }

}
