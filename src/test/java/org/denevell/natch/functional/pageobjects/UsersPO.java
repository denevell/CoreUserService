package org.denevell.natch.functional.pageobjects;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.xml.bind.annotation.XmlRootElement;

import org.denevell.natch.functional.TestUtils;
import org.denevell.natch.functional.pageobjects.UserPO.User;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class UsersPO {

	private WebTarget mService;

	public UsersPO() {
		Client client = JerseyClientBuilder.createClient();
		client.register(JacksonFeature.class);
		mService = client.target(TestUtils.URL_USER_SERVICE);
	}

	public UserList listUsers(String authKey) {
        return mService 
        .path("rest").path("user").path("list").request()
        .header("AuthKey", authKey)
        .get(UserList.class);
	}
	
	public User findUser(String name, String authKey) {
		List<User> users = listUsers(authKey).getUsers();
		for (User user : users) {
			if(user.getUsername().equals(name)) {
				return user;
			}
		}
		return null;
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