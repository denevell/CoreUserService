package org.denevell.userservice;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;


@Path("example")
public class ExampleRequest {

	@Path("{example}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ExampleResource> example(@PathParam("example") String example) {
		
		// Get the EntityManager by creating an EntityManagerFactory via the persistence-unit name we provided.
		EntityManager entityManager = Persistence.createEntityManagerFactory("PERSISTENCE_UNIT_NAME").createEntityManager();   		
		// Start a transaction - not needed in this case, but useful to see
		EntityTransaction transaction = entityManager.getTransaction();
		List<ExampleEntity> list  = null;
		try {
			transaction.begin();
			
			// Add an entity
			ExampleEntity entity = new ExampleEntity();
			entity.setTalky(example);			
			entityManager.persist(entity);
			
			// List entities, via the named query we defined in mapping.xml
			TypedQuery<ExampleEntity> nq = entityManager.createNamedQuery("list", ExampleEntity.class);
			list = nq.getResultList();
			
			transaction.commit();
		} catch (Exception e) {
			Logger.getLogger(getClass()).error("Problem persisting", e);
			transaction.rollback();
			throw e; // Ergo showing a 500 error. You may want to throw an exception that's not detailing stuff about your JPA connection
		} finally {
			entityManager.clear(); // Clears all the entities from the EntityManager
			entityManager.close();
		}
		
		// Adapt the entities into objects to return as JSON
		ArrayList<ExampleResource> resList = new ArrayList<ExampleResource>();
		for (ExampleEntity exampleEntity : list) {
			ExampleResource exampleItem = new ExampleResource();
			exampleItem.setStuff(exampleEntity.getTalky());
			resList.add(exampleItem);
		}
		
		return resList;
	}

}
