package com.lethanh98.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Table(name = "user")
@Entity
public class User {

  public User() {
  }

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private int id;

  private String firstName;

  private String lastName;

}
