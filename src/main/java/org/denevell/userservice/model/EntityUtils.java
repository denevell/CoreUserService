package org.denevell.userservice.model;

import javax.persistence.EntityManager;

import org.denevell.userservice.Log;

public class EntityUtils {
	
	public static void closeEntityConnection(EntityManager entityManager) {
		try {
			if(entityManager!=null) entityManager.clear();
			if(entityManager!=null) entityManager.close();
		} catch(Exception e) {
			e.printStackTrace();
			Log.info(EntityUtils.class, e.toString());
		}
	}		

}
