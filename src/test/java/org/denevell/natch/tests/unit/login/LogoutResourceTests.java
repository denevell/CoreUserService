package org.denevell.natch.tests.unit.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletResponse;

import org.denevell.userservice.model.LogoutModel;
import org.denevell.userservice.serv.LogoutRequest;
import org.denevell.userservice.serv.LogoutRequest.LogoutResourceReturnData;
import org.junit.Before;
import org.junit.Test;

public class LogoutResourceTests {
	
	private LogoutRequest resource;
	private LogoutModel userModel;
	private HttpServletResponse response;

	@Before
	public void setup() {

		response = mock(HttpServletResponse.class);
		userModel = mock(LogoutModel.class);
		resource = new LogoutRequest(userModel, response);
	}
	
	@Test
	public void shouldLogout() throws Exception {
		// Arrange
		when(userModel.logout("asdf")).thenReturn(LogoutModel.SUCCESS);
		
		// Act
		LogoutResourceReturnData result = resource.logout("asdf");
		
		// Assert
		assertTrue(result.isSuccessful());
		assertEquals("Error json", "", result.getError());
	}
	
	@Test
	public void shouldntLogoutOnModelError() throws Exception{
		// Arrange
		when(userModel.logout("asdf")).thenReturn(LogoutModel.FAIL);
		// Act
		LogoutResourceReturnData result = resource.logout("asdf");
		
		// Assert
		assertEquals("Excepting the success result to be false", false, result.isSuccessful());
	}
	
}