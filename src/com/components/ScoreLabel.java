package com.components;

import javax.swing.*;

public class ScoreLabel extends JLabel {
  private int score = 0;
  private double angel = 45;
  private String corner = "top-left";

  public ScoreLabel() {
    this.setText("Pass: " + this.score + "; Corner: " + this.corner + "; Angel: " + this.angel);
  }

  public void setScore(int score) {
    this.score = score;
    this.setText("Pass: " + this.score + "; Corner: " + this.corner + "; Angel: " + this.angel);
  }

  public void increaseScore() {
    this.score += 1;
    this.setText("Pass: " + this.score + "; Corner: " + this.corner + "; Angel: " + this.angel);
  }

  public void setAngel(double angel) {
    this.angel = angel;
  }

  public void setCorner(String corner) {
    this.corner = corner;
  }
}
