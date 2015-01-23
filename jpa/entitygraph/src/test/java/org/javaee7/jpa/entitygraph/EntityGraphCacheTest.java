package org.javaee7.jpa.entitygraph;


import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnitUtil;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class EntityGraphCacheTest {

	@PersistenceContext
	private EntityManager entityManager;
	@Inject
	private MovieBean movieBean;

	@Deployment
	public static WebArchive createDeployment() {
		WebArchive war = ShrinkWrap.create(WebArchive.class)
		                           .addPackage("org.javaee7.jpa.entitygraph")
		                           .addAsResource("META-INF/cache-persistence.xml", "META-INF/persistence.xml")
		                           .addAsResource("META-INF/create.sql")
		                           .addAsResource("META-INF/drop.sql")
		                           .addAsResource("META-INF/load.sql");
		System.out.println(war.toString(true));
		return war;
	}

	@Test
	public void testEntityGraphWithCache() throws Exception {
		PersistenceUnitUtil persistenceUnitUtil = entityManager.getEntityManagerFactory().getPersistenceUnitUtil();

		Movie movie = movieBean.findMovieById(1, "javax.persistence.fetchgraph", "movieWithActors");
		assertTrue("movieActors must be eagerly loaded", persistenceUnitUtil.isLoaded(movie, "movieActors"));

		movie = movieBean.findMovieById(1, "javax.persistence.fetchgraph", "movieWithActors");
		assertTrue("movieActors must be eagerly loaded when cached", persistenceUnitUtil.isLoaded(movie, "movieActors"));
	}
}
