package com.components;

import java.awt.*;
import javax.swing.*;

public class CanvasPanel extends JPanel {
  public int width;
  public int height;

  CanvasPanel(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public Dimension getPreferredSize() {
    return new Dimension(this.height, this.width);
  }
}
