package com.ouisncf.inno.agora.propositionengine.category;

import java.util.ArrayList;
import java.util.List;

public class Categories extends ArrayList<Category> {

  private Categories(List<Category> categories) {
    super(categories);
  }

  public static Builder builder(){
    return new Builder();
  }

  public static class Builder{

    List<Category> categories = new ArrayList<>();

    public Builder withCategory(String name, List<String> elements, float weight) {
      categories.add(new Category(name, elements, weight));
      return this;
    }

    public Builder withCategory(String name, List<String> elements) {
      categories.add(new Category(name, elements));
      return this;
    }

    public Categories buildWithEqualWeight(){
       categories.forEach(category -> category.setWeight(1f/categories.size()));
       return new Categories(categories);
    }

    public Categories build(){
      return new Categories(categories);
    }

  }






}
