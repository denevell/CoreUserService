package org.denevell.natch.functional.pageobjects;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import org.denevell.natch.functional.TestUtils;
import org.denevell.userservice.serv.UserRequest.User;
import org.denevell.userservice.serv.UsersListRequest.UserList;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class ListUsersPO {

	private WebTarget mService;

	public ListUsersPO() {
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
}