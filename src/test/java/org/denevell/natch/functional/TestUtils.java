package org.denevell.natch.functional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class TestUtils {
	
	public static final String URL_USER_SERVICE = "http://localhost:8083/";

	public static void deleteTestDb() throws Exception {
        Properties connectionProps = new Properties();
        connectionProps.put("user", "denevell");
        connectionProps.put("password", "user");

        @SuppressWarnings("unused")
		Class<?> c = Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://" +
                		"localhost" +
                        ":5432" + "/testnatch",
                connectionProps);

        Statement statement = conn.createStatement();
        statement.execute("delete from users_loggedin");
        statement.execute("delete from UserEntity");
	}	

}
