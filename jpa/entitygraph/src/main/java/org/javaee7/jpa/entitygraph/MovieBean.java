package org.javaee7.jpa.entitygraph;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Arun Gupta
 */
@SuppressWarnings("unchecked")
@Stateless
public class MovieBean {
    @PersistenceContext
    private EntityManager entityManager;

    public Movie findMovieById(Integer movieId, String hint, String graphName) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(hint, entityManager.getEntityGraph(graphName));

        return entityManager.find(Movie.class, movieId, properties);
    }

    public List<Movie> listMovies() {
        return entityManager.createNamedQuery("Movie.findAll")
                            .getResultList();
    }

    public List<Movie> listMovies(String hint, String graphName) {
        return entityManager.createNamedQuery("Movie.findAll")
                            .setHint(hint, entityManager.getEntityGraph(graphName))
                            .getResultList();
    }

    public List<Movie> listMovies(String hint, EntityGraph<?> entityGraph) {
        return entityManager.createNamedQuery("Movie.findAll")
                            .setHint(hint, entityGraph)
                            .getResultList();
    }

    public List<Movie> listMoviesById(Integer movieId, String hint, String graphName) {
        return entityManager.createNamedQuery("Movie.findAllById")
                            .setParameter("movieId", movieId)
                            .setHint(hint, entityManager.getEntityGraph(graphName))
                            .getResultList();
    }

    public List<Movie> listMoviesByIds(List<Integer> movieIds, String hint, String graphName) {
        return entityManager.createNamedQuery("Movie.findAllByIds")
                            .setParameter("movieIds", movieIds)
                            .setHint(hint, entityManager.getEntityGraph(graphName))
                            .getResultList();
    }
}
