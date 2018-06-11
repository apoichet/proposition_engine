package com.ouisncf.inno.agora.propositionengine.category;

import java.util.List;

public class Category{

  private String name;
  private float weight;
  private List<String> elements;

  public String getName() {
    return name;
  }

  public List<String> getElements() {
    return elements;
  }

  public float getWeight() {
    return weight;
  }

  public void setWeight(float weight) {
    this.weight = weight;
  }

  public Category(String name, List<String> elements, float weight) {
    this.name = name;
    this.weight = weight;
    this.elements = elements;
  }

  public Category(String name, List<String> elements) {
    this.name = name;
    this.elements = elements;
  }
}
