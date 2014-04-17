package org.denevell.natch.tests.unit.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletResponse;

import org.denevell.natch.io.users.LogoutResourceReturnData;
import org.denevell.userservice.model.interfaces.UserLogoutModel;
import org.denevell.userservice.serv.LogoutRequest;
import org.junit.Before;
import org.junit.Test;

public class LogoutResourceTests {
	
	private LogoutRequest resource;
	private UserLogoutModel userModel;
	private HttpServletResponse response;

	@Before
	public void setup() {

		response = mock(HttpServletResponse.class);
		userModel = mock(UserLogoutModel.class);
		resource = new LogoutRequest(userModel, response);
	}
	
	@Test
	public void shouldLogout() throws Exception {
		// Arrange
		when(userModel.logout("asdf")).thenReturn(UserLogoutModel.SUCCESS);
		
		// Act
		LogoutResourceReturnData result = resource.logout("asdf");
		
		// Assert
		assertTrue(result.isSuccessful());
		assertEquals("Error json", "", result.getError());
	}
	
	@Test
	public void shouldntLogoutOnModelError() throws Exception{
		// Arrange
		when(userModel.logout("asdf")).thenReturn(UserLogoutModel.FAIL);
		// Act
		LogoutResourceReturnData result = resource.logout("asdf");
		
		// Assert
		assertEquals("Excepting the success result to be false", false, result.isSuccessful());
	}
	
}