package org.denevell.natch.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.WebApplicationException;

import org.denevell.natch.functional.pageobjects.AdminTogglePO;
import org.denevell.natch.functional.pageobjects.ListUsersPO;
import org.denevell.natch.functional.pageobjects.LoginPO;
import org.denevell.natch.functional.pageobjects.RegisterPO;
import org.denevell.userservice.SuccessOrError;
import org.denevell.userservice.serv.LoginRequest.LoginResourceReturnData;
import org.denevell.userservice.serv.UserRequest.User;
import org.denevell.userservice.serv.UsersListRequest.UserList;
import org.junit.Before;
import org.junit.Test;

public class UserAdminToggleFunctional {
	
	private RegisterPO registerPo;
	private ListUsersPO listUsersPO;
	private AdminTogglePO toggleAdminPO;

	@Before
	public void setup() throws Exception {
	    registerPo = new RegisterPO();
	    listUsersPO = new ListUsersPO();
	    toggleAdminPO = new AdminTogglePO();
		TestUtils.deleteTestDb();
	}
	
	@Test
	public void shouldToggleUserAdmin() {
		// Arrange 
	    registerPo.register("aaron", "aaron");
	    registerPo.register("other1", "other1");
		LoginResourceReturnData loginResult = new LoginPO().login("aaron", "aaron");
		UserList users = listUsersPO.listUsers(loginResult.getAuthKey());
		User user = users.getUsers().get(1);
		if(!user.getUsername().equals("other1")) {
			user = users.getUsers().get(0);
			assertEquals("aaron", users.getUsers().get(1).getUsername());
		}
		assertEquals("other1", user.getUsername());
		assertEquals(false, users.getUsers().get(1).isAdmin());

	    // Act
        SuccessOrError result = toggleAdminPO.toggle(loginResult.getAuthKey());

		// Assert
        assertTrue("Is successful", result.isSuccessful());
        users = listUsersPO.listUsers(loginResult.getAuthKey());
		user = users.getUsers().get(1);
		if(!user.getUsername().equals("other1")) {
			user = users.getUsers().get(0);
			assertEquals("aaron", users.getUsers().get(1).getUsername());
		}
		assertEquals("other1", user.getUsername());
		assertEquals(true, user.isAdmin());

	    // Act
        result = toggleAdminPO.toggle(loginResult.getAuthKey());

		// Assert
        assertTrue("Is successful", result.isSuccessful());
        users = listUsersPO.listUsers(loginResult.getAuthKey());
		user = users.getUsers().get(1);
		if(!user.getUsername().equals("other1")) {
			user = users.getUsers().get(0);
			assertEquals("aaron", users.getUsers().get(1).getUsername());
		}
		assertEquals("other1", user.getUsername());
		assertEquals(false, user.isAdmin());
	}

	@Test
	public void shouldToggleWorksImmediately() {
		// Arrange 
	    registerPo.register("aaron", "aaron");
	    registerPo.register("other1", "other1");
		LoginResourceReturnData loginResultAdmin = new LoginPO().login("aaron", "aaron");
		LoginResourceReturnData loginResultUser = new LoginPO().login("other1", "other1");
        UserList users = listUsersPO.listUsers(loginResultAdmin.getAuthKey());
		User user = users.getUsers().get(1);
		if(!user.getUsername().equals("other1")) {
			user = users.getUsers().get(0);
			assertEquals("aaron", user.getUsername());
		}
		assertEquals("other1", user.getUsername());
		assertEquals(false, user.isAdmin());

	    // Act - make normal user an admin
        toggleAdminPO.toggle(loginResultAdmin.getAuthKey());
	    // Act - now back to a non-admin
        toggleAdminPO.toggle(loginResultAdmin.getAuthKey());

		// Assert - the normal user can run an admin commnad, i.e. toggle admin 
	    // Act
		try {
			toggleAdminPO.toggle(loginResultUser.getAuthKey());
        } catch (WebApplicationException e) {
            assertTrue("Get a 401 when not an admin", e.getResponse().getStatus()==401);
            return;
        }
		assertFalse("Was excepting an exception", true);
	}

	@Test
	public void shouldntToggleAdminIfNotAdmin() {
		// Arrange 
	    registerPo.register("aaron", "aaron");
	    registerPo.register("other1", "other1");
		LoginResourceReturnData loginResult = new LoginPO().login("other1", "other1");

	    // Act
		try {
			toggleAdminPO.toggle(loginResult.getAuthKey());
        } catch (WebApplicationException e) {
            assertTrue("Get a 401 when not an admin", e.getResponse().getStatus()==401);
            return;
        }
		assertFalse("Was excepting an exception", true);
	}
	
}
