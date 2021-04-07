package com;

import com.components.GeneralFrame;

public class Main {
  public static void main(String[] args) {
    GeneralFrame frame = new GeneralFrame();
    frame.setSize(800, 800);
    frame.setVisible(true);

    // WaveAnimation wa = new WaveAnimation(frame.getCanvasLabel(), frame.getImageHelper(),
    // frame.getScoreLabel());
    // wa.start();
  }
}
