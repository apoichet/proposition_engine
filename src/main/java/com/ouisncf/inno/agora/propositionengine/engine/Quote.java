package com.ouisncf.inno.agora.propositionengine.engine;

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;

public class Quote implements Serializable {

  private String outwardOrigin;
  private String outwardDestination;
  private float price;
  private String departureDate;
  private String arrivalDate;
  private String urlImage;

  public Quote() {
  }

  public String getUrlImage() {
    return urlImage;
  }

  public void setUrlImage(String urlImage) {
    this.urlImage = urlImage;
  }

  public String getOutwardOrigin() {
    return outwardOrigin;
  }

  public void setOutwardOrigin(String outwardOrigin) {
    this.outwardOrigin = outwardOrigin;
  }

  public String getOutwardDestination() {
    return outwardDestination;
  }

  public void setOutwardDestination(String outwardDestination) {
    this.outwardDestination = outwardDestination;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public String getDepartureDate() {
    return departureDate;
  }

  public void setDepartureDate(String departureDate) {
    this.departureDate = departureDate;
  }

  public String getArrivalDate() {
    return arrivalDate;
  }

  public void setArrivalDate(String arrivalDate) {
    this.arrivalDate = arrivalDate;
  }

  public static String getCityCode(String city){

    switch (city){
      case "Lyon": return "FRLPD";
      case "Marseille": return "FRMRS";
      case "Bordeaux": return "FRBOJ";

    }
    return StringUtils.EMPTY;
  }

  public static String getUrlCity(String city){

    switch (city){
      case "Lyon": return "http://www.olivierlegrand.fr/wp-content/uploads/2017/11/lyon.jpg";
      case "Marseille": return "http://ekladata.com/V-tdNTaDCemcedjo3mz0TK3hFHc.jpg";
      case "Bordeaux": return "http://www.properties-in-charente.com/images/bord.jpg";

    }
    return StringUtils.EMPTY;
  }


  @Override
  public String toString() {
    return "Quote{" +
      "outwardOrigin='" + outwardOrigin + '\'' +
      ", outwardDestination='" + outwardDestination + '\'' +
      ", price=" + price +
      ", departureDate='" + departureDate + '\'' +
      ", arrivalDate='" + arrivalDate + '\'' +
      '}';
  }
}
