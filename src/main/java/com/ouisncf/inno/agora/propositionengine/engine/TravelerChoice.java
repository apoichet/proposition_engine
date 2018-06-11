package com.ouisncf.inno.agora.propositionengine.engine;

import java.io.Serializable;

public class TravelerChoice implements Serializable {

  private long id;
  private String firstName;
  private String destination;
  private String date;
  private String price;

  public TravelerChoice() {
  }

  public TravelerChoice(String destination, String date, String price) {
    this.destination = destination;
    this.date = date;
    this.price = price;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }
}
