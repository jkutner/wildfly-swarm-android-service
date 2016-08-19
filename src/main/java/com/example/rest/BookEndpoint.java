package com.example.rest;

import com.example.models.Book;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/books")
@ApplicationScoped
public class BookEndpoint {

  @PersistenceContext
  EntityManager em;

  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  public Book[] all() {
    return em.createNamedQuery("Book.findAll", Book.class)
        .getResultList()
        .toArray(new Book[0]);
  }

  @GET
  @Path("/{isbn}")
  @Produces(MediaType.APPLICATION_JSON)
  public Book get(@PathParam("isbn") String isbn){
    return em.createNamedQuery("Book.find", Book.class)
        .setParameter("isbn", isbn)
        .getResultList()
        .get(0);

  }

  @POST
  @Path("/new")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Transactional
  public Book post(Book book){
    em.persist(book);
    return get(book.getIsbn());
  }
}
