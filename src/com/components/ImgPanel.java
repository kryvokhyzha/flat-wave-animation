package com.components;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class ImgPanel extends JPanel {
  private BufferedImage image;

  public ImgPanel(BufferedImage image) {
    this.image = image;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (this.image != null) g.drawImage(this.image, 0, 0, null);
  }
}
