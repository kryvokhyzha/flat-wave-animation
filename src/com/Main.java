package com;

import com.components.ImgPanel;
import com.components.SettingsPanel;
import com.components.Wave;
import com.utils.ImageHelper;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Paths;
import javax.swing.*;

public class Main {
  public static void main(String[] args) throws IOException {
    JFrame frame = new GeneralFrame();
    ImageHelper imageHelper = new ImageHelper(String.valueOf(Paths.get("resources/img1.jpg")));
    Wave wave = new Wave(0, 0, imageHelper.getWidth(), imageHelper.getHeight());

    ImgPanel imgPanel = new ImgPanel(imageHelper.getImage(), wave);
    SettingsPanel settingsPanel = new SettingsPanel();

    Container content = frame.getContentPane();
    content.add(imgPanel);

    content.add(settingsPanel, BorderLayout.SOUTH);

    frame.setSize((int) (imageHelper.getWidth() * 1.25), (int) (imageHelper.getHeight() * 1.25));

    int c = 0;
    double angel = 45;
    int xCurrent = 0;
    int yCurrent = 0;

    while (true) {
      for (xCurrent = 0; xCurrent < imageHelper.getWidth(); xCurrent++) {
        yCurrent = Wave.waveFunction(xCurrent, c, angel);
        wave.setxPoint(xCurrent, xCurrent);
        wave.setyPoint(xCurrent, yCurrent);
      }
      imgPanel.setWave(wave);
      imgPanel.revalidate();
      imgPanel.repaint();
      try {
        Thread.sleep(5);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      c++;
    }
  }

  public static void startAnimation() {}
}
