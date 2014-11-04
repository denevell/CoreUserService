package org.denevell.natch.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.WebApplicationException;

import org.denevell.natch.functional.pageobjects.LoginPO;
import org.denevell.natch.functional.pageobjects.LoginPO.LoginResourceReturnData;
import org.denevell.natch.functional.pageobjects.RegisterPO;
import org.denevell.natch.functional.pageobjects.UserPO;
import org.denevell.natch.functional.pageobjects.UserPO.User;
import org.junit.Before;
import org.junit.Test;

public class LoginFunctional {
	
	private RegisterPO registerPo;
  private UserPO userPo;

	@Before
	public void setup() throws Exception {
		registerPo = new RegisterPO();
		userPo = new UserPO();
		TestUtils.deleteTestDb();
	}
	
	@Test
	public void shouldLoginWithGoodCredentials() {
		// Arrange 
	    registerPo.register("aaron@aaron.com", "passy");
		LoginResourceReturnData loginResult = new LoginPO().login("aaron@aaron.com", "passy");
		
		// Assert
		assertEquals("", loginResult.getError());
		assertTrue("Should return true as 'successful' field", loginResult.isSuccessful());
	}

	
	@Test
	public void shouldSee403OnBadCredentials() {
	    registerPo.register("aaron@aaron.com", "passy");
		try{
			new LoginPO().login("aaron@aaron.com", "passyWRONG");
		} catch(WebApplicationException e) {
			assertEquals(403, e.getResponse().getStatus());
			return;
		}
		assertFalse("Excepted a 403", true);	
	}
	

	@Test
	public void login_shouldSeeJsonErrorOnBlanksPassed() {
		try{
			new LoginPO().login(" ", " ");
		} catch(BadRequestException e) {
			assertEquals(400, e.getResponse().getStatus());
			return;
		}
		assertFalse("Excepted a 400", true);	
	}
	
	@Test
	public void login_shouldSeeJsonErrorOnBlankUsername() {
		try{
			new LoginPO().login(" ", "password");
		} catch(BadRequestException e) {
			assertEquals(400, e.getResponse().getStatus());
			return;
		}
		assertFalse("Excepted a 400", true);	
	}
	
	@Test
	public void login_shouldSeeJsonErrorOnBlankPassword() {
		try{
			new LoginPO().login("username", " ");
		} catch(BadRequestException e) {
			assertEquals(400, e.getResponse().getStatus());
			return;
		}
		assertFalse("Excepted a 400", true);	
	}
	
	@Test
	public void login_shouldSeeJsonErrorOnNullUsername() {
		try{
			new LoginPO().login(null, "password");
		} catch(BadRequestException e) {
			assertEquals(400, e.getResponse().getStatus());
			return;
		}
		assertFalse("Excepted a 400", true);	
	}
	
	@Test
	public void login_shouldSeeJsonErrorOnNullPassword() {
		try{
			new LoginPO().login("username", null);
		} catch(BadRequestException e) {
			assertEquals(400, e.getResponse().getStatus());
			return;
		}
		assertFalse("Excepted a 400", true);	
	}
	
	@Test
	public void login_shouldSeeJsonErrorOnBadJson() {
		// Deferred non functional requirement
	}
	
	@Test
	public void login_shouldBeAbleToLoginTwice() {
		// Arrange 
	    registerPo.register("aaron@aaron.com", "passy");
		LoginResourceReturnData loginResult = new LoginPO().login("aaron@aaron.com", "passy");
		LoginResourceReturnData loginResult2 = new LoginPO().login("aaron@aaron.com", "passy");
		
		// Assert
		assertEquals("", loginResult.getError());
		assertTrue("Should return true as 'successful' field for first login", loginResult.isSuccessful());		
		assertEquals("", loginResult2.getError());
		assertTrue("Should return true as 'successful' field for second login", loginResult2.isSuccessful());		
	}
	
	@Test
	public void shouldReturnAuthKeyOnLogin() {
		// Arrange 
	    registerPo.register("aaron@aaron.com", "passy");
		LoginResourceReturnData loginResult = new LoginPO().login("aaron@aaron.com", "passy");
		
		// Assert
		assertTrue("Should return auth key", loginResult.getAuthKey().length()>5);
	}
	
	@Test
	public void shouldReturnDifferentAuthKeys() {
		// Arrange 
	    registerPo.register("aaron@aaron.com", "passy");
		LoginResourceReturnData loginResult = new LoginPO().login("aaron@aaron.com", "passy");
		LoginResourceReturnData loginResult1 = new LoginPO().login("aaron@aaron.com", "passy");
		
		// Assert
		assertFalse("Should return different auth key", loginResult.getAuthKey().equals(loginResult1.getAuthKey()));		
	}
	
	@Test
	public void shouldLoginWithAuthKey() {
	  registerPo.register("aaron@aaron.com", "passy");
		LoginResourceReturnData loginResult = new LoginPO().login("aaron@aaron.com", "passy");
	    
	  // Act
		User u = userPo.user(loginResult.getAuthKey());
		
		// Assert
		assertTrue("Should be logged in to do this", u.getUsername().equals("aaron@aaron.com")); 
	}
	
	@Test
	public void shouldntLoginWithBadAuthKey() {
		// Arrange 
	  registerPo.register("aaron@aaron.com", "passy");
		LoginResourceReturnData loginResult = new LoginPO().login("aaron@aaron.com", "passy");
		loginResult.getAuthKey();
	    
	  // Act
		try {
		  userPo.user(loginResult.getAuthKey()+"INCORRECT");
		} catch(WebApplicationException e) {
			// Assert
			assertEquals("Should get 404", 404, e.getResponse().getStatus()); 
			return;
		}
		assertTrue("Wanted to see a 401", false);
	}
	
	@Test
	public void shouldLoginWithOldAuthKey() {
	  // Login and check your logged in
	  registerPo.register("aaron@aaron.com", "passy");
		LoginResourceReturnData loginResult = new LoginPO().login("aaron@aaron.com", "passy");
		User u = userPo.user(loginResult.getAuthKey());
		assertTrue("Should be logged in to do this", u.getUsername().equals("aaron@aaron.com")); 

		// User a new key
		LoginResourceReturnData newLoginResult = new LoginPO().login("aaron@aaron.com", "passy");
		u = userPo.user(newLoginResult.getAuthKey());
		assertTrue("Should be logged in to do this", u.getUsername().equals("aaron@aaron.com")); 
		
		// Use the old key too
		u = userPo.user(loginResult.getAuthKey());
		assertTrue("Should be logged in to do this", u.getUsername().equals("aaron@aaron.com")); 
	}	
	
}
