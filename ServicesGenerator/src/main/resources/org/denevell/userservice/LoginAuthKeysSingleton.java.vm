package org.denevell.userservice;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class LoginAuthKeysSingleton {
	private static LoginAuthKeysSingleton sInstance;
	private Map<String, UserEntity> mLoginKeys = Collections.synchronizedMap(new HashMap<String, UserEntity>());
	
	private LoginAuthKeysSingleton() {
	}
	
	public static LoginAuthKeysSingleton getInstance() {
		if(sInstance==null) {
			sInstance = new LoginAuthKeysSingleton();
		} 
		return sInstance;
	}

	public String generateAndStore(UserEntity user) {
		if(user==null || user.getUsername()==null || user.getUsername().trim().length()==0) {
			return null;
		}
		String key = UUID.randomUUID().toString();
		mLoginKeys.put(key, user);
		return key;
	}

	public UserEntity retrieveUserEntity(String key) {
		UserEntity user = mLoginKeys.get(key);
		return user;
	}
	
	/**
	 * @return entity removed
	 */
	public UserEntity remove(String authKey) {
		return mLoginKeys.remove(authKey);
	}

	public UserEntity getLoggedinUser(String userName) {
		Map<String, UserEntity> userKey = mLoginKeys;
		for (Entry<String, UserEntity> keyUserEntity : userKey.entrySet()) {
		    if(keyUserEntity.getValue().getUsername().equals(userName)) {
		        return keyUserEntity.getValue();
		    }
        }
		return null;
	}

	public void clearAllKeys() {
		mLoginKeys.clear();
	}
	
	public void kill() {
		mLoginKeys = null;
		sInstance = null;
	}

}
