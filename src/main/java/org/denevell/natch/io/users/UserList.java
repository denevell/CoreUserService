package org.denevell.natch.io.users;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserList {
	
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
