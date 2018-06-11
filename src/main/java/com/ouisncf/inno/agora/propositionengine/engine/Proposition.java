package com.ouisncf.inno.agora.propositionengine.engine;

import java.io.Serializable;

public class Proposition implements Serializable {

  private String destination;
  private String price;
  private String departureDate;
  private float score;

  public Proposition() {
  }

  public Proposition(String destination, String departureDate, String price, float score) {
    this.destination = destination;
    this.price = price;
    this.departureDate = departureDate;
    this.score = score;
  }

  public Proposition(String destination, String departureDate, String price) {
    this.destination = destination;
    this.price = price;
    this.departureDate = departureDate;
  }

  public float getScore() {
    return score;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getDepartureDate() {
    return departureDate;
  }

  public void setDepartureDate(String departureDate) {
    this.departureDate = departureDate;
  }

  public void setScore(float score) {
    this.score = score;
  }
}
