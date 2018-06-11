package com.ouisncf.inno.agora.propositionengine.engine;

public class PollScore {

  private float weightCategory;
  public int score;

  public PollScore(float weightCategory) {
    this.weightCategory = weightCategory;
  }

  public float calculScore(){
    return weightCategory*score;
  }

  public float getWeightCategory() {
    return weightCategory;
  }
  
}
