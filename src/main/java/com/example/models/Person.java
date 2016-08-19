package com.example.models;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "REST_DB_ACCESS")
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT e FROM Person e"),
    @NamedQuery(name = "Person.find", query = "SELECT e FROM Person e WHERE isbn = :isbn")
})
@XmlRootElement
public class Person {
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
