package com.components;

import com.utils.CornerPosition;
import javax.swing.*;

public class ScoreLabel extends JLabel {
  private int score = 0;
  private double angel = 45;
  private String corner = CornerPosition.TOPLEFT.getPosition();

  public ScoreLabel() {
    updateLabelText();
  }

  public void setScore(int score) {
    this.score = score;
    updateLabelText();
  }

  public void increaseScore() {
    this.score += 1;
    updateLabelText();
  }

  private void updateLabelText() {
    this.setText(
        String.format("Pass: %d; Corner: %s; Angel: %.2f", this.score, this.corner, this.angel));
  }

  public void setAngel(double angel) {
    this.angel = angel;
  }

  public void setCorner(String corner) {
    this.corner = corner;
  }
}
