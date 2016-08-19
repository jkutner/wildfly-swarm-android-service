package com.example.rest;

import com.example.models.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/books")
@ApplicationScoped
public class PersonEndpoint {

  @PersistenceContext
  EntityManager em;

  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  public Person[] all() {
    return em.createNamedQuery("Person.findAll", Person.class)
        .getResultList()
        .toArray(new Person[0]);
  }

  @GET
  @Path("/{isbn}")
  @Produces(MediaType.APPLICATION_JSON)
  public Person get(@PathParam("isbn") String isbn){
    return em.createNamedQuery("Person.find", Person.class)
        .setParameter("isbn", isbn)
        .getResultList()
        .get(0);

  }

  @POST
  @Path("/new")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Transactional
  public Person post(Person book){
    em.persist(book);
    return get(book.getIsbn());
  }
}
