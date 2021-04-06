package com.components;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class ImgPanel extends JPanel {
  private BufferedImage image;
  private Wave wave;

  public ImgPanel(BufferedImage image, Wave wave) {
    this.image = image;
    this.wave = wave;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }

  public void setWave(Wave wave) {
    this.wave = wave;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (this.image != null) g.drawImage(this.image, 0, 0, null);

    if (this.wave != null) g.setColor(Color.RED);
    g.drawPolyline(this.wave.getxPoints(), this.wave.getyPoints(), this.wave.getnPoints());
  }
}
