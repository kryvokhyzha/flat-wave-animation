package com.components;

import com.utils.CornerPosition;
import javax.swing.*;

public class ScoreLabel extends JLabel {
  private int score = 0;
  private double angle = 45;
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
        String.format("Pass: %d; Corner: %s; Angle: %.2f", this.score, this.corner, this.angle));
  }

  public void setAngle(double angle) {
    this.angle = angle;
  }

  public void setCorner(String corner) {
    this.corner = corner;
  }
}
