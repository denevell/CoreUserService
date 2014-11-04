package org.denevell.natch.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.WebApplicationException;

import org.denevell.natch.functional.pageobjects.LoginPO;
import org.denevell.natch.functional.pageobjects.LoginPO.LoginResourceReturnData;
import org.denevell.natch.functional.pageobjects.LogoutPO;
import org.denevell.natch.functional.pageobjects.LogoutPO.LogoutResourceReturnData;
import org.denevell.natch.functional.pageobjects.RegisterPO;
import org.denevell.natch.functional.pageobjects.UserPO;
import org.denevell.natch.functional.pageobjects.UserPO.User;
import org.junit.Before;
import org.junit.Test;

public class LogoutFunctional {
	
	private LogoutPO logoutPo;
	private RegisterPO registerPo;
  private UserPO userPo;
	
	@Before
	public void setup() throws Exception {
		logoutPo = new LogoutPO();
		registerPo = new RegisterPO();
		userPo = new UserPO();
		TestUtils.deleteTestDb();
	}
	
	@Test
	public void shouldLogout() {
		// Arrange
	    registerPo.register("aaron@aaron.com", "passy");
		LoginResourceReturnData loginResult = new LoginPO().login("aaron@aaron.com", "passy");
		
		// Act
		LogoutResourceReturnData logoutData = logoutPo.logout(loginResult.getAuthKey());
		
		// Assert
		assertTrue(logoutData.isSuccessful());
	}
	
	@Test
	public void shouldntLogoutIfBadAuthData() {
		// Arrange
	    registerPo.register("aaron@aaron.com", "passy");
		LoginResourceReturnData loginResult = new LoginPO().login("aaron@aaron.com", "passy");
		
		// Act
		try {
			logoutPo.logout(loginResult.getAuthKey()+"blar");
		} catch(WebApplicationException e) {
			// Assert
			assertEquals(401, e.getResponse().getStatus());
			return;
		}
		assertFalse("Was excepting a 401 response", true);
		
	}

	@Test
	public void shouldLoginTwiceThenLogoutWithOneAndStillBeAbleToUseTheOther() {
		// Arrange 
	  registerPo.register("aaron@aaron.com", "passy");
		LoginResourceReturnData loginResult = new LoginPO().login("aaron@aaron.com", "passy");
		LoginResourceReturnData otherLoginResult = new LoginPO().login("aaron@aaron.com", "passy");
		logoutPo.logout(loginResult.getAuthKey());

	  // Act
		User u = userPo.user(otherLoginResult.getAuthKey());
		assertTrue("Should be logged in to do this", u.getUsername().equals("aaron@aaron.com")); 
	}	
	
}
