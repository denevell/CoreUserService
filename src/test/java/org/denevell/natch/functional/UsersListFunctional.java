package org.denevell.natch.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.WebApplicationException;

import org.denevell.natch.functional.pageobjects.UsersPO;
import org.denevell.natch.functional.pageobjects.LoginPO;
import org.denevell.natch.functional.pageobjects.RegisterPO;
import org.denevell.userservice.serv.LoginRequest.LoginResourceReturnData;
import org.denevell.userservice.serv.UserRequest.User;
import org.denevell.userservice.serv.UsersListRequest.UserList;
import org.junit.Before;
import org.junit.Test;

public class UsersListFunctional {
	
	private RegisterPO registerPo;
	private UsersPO listUsersPO;

	@Before
	public void setup() throws Exception {
	    registerPo = new RegisterPO();
	    listUsersPO = new UsersPO();
		TestUtils.deleteTestDb();
	}
	
	@Test
	public void shouldListUsersAsAdmin() {
		// Arrange 
	    registerPo.register("aaron", "aaron");
	    registerPo.register("other1", "other1");
		LoginResourceReturnData loginResult = new LoginPO().login("aaron", "aaron");

	    // Act
		UserList thread = listUsersPO.listUsers(loginResult.getAuthKey());	
		
		// Assert
		List<User> users = thread.getUsers();
		User user0 = listUsersPO.findUser("aaron", loginResult.getAuthKey());
		User user1 = listUsersPO.findUser("other1", loginResult.getAuthKey());
		assertEquals(2, users.size());
		assertEquals(true, user0.isAdmin());
		assertEquals(false, user1.isAdmin());
	}

    @Test
    public void shouldntListUsersAsNormalUser() {
        // Arrange 
	    registerPo.register("aaron", "aaron");
	    registerPo.register("other1", "other1");
		LoginResourceReturnData loginResult = new LoginPO().login("other1", "other1");

        // Act
        try {
        	listUsersPO.listUsers(loginResult.getAuthKey());	
        } catch (WebApplicationException e) {
            assertEquals(401, e.getResponse().getStatus());
            return;
        }
        assertTrue("Was exception exception", false);
    }	

}
