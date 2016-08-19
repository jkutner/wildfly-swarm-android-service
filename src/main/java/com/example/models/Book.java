package com.example.models;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "REST_DB_ACCESS")
@NamedQueries({
    @NamedQuery(name = "Book.findAll", query = "SELECT e FROM Book e"),
    @NamedQuery(name = "Book.find", query = "SELECT e FROM Book e WHERE isbn = :isbn")
})
@XmlRootElement
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(length = 40)
  private String isbn;

  public Integer getId() {
    return id;
  }

  public void setId(Integer i) {
    id = i;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String s) {
    this.isbn = s;
  }
}
