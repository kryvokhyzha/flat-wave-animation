package com;

import com.components.ImgPanel;
import com.components.SettingsPanel;
import com.sequential.WaveAnimation;
import com.utils.ImageHelper;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Paths;
import javax.swing.*;

public class Main {
  public static void main(String[] args) throws IOException {
    JFrame frame = new GeneralFrame();
    ImageHelper imageHelper = new ImageHelper(String.valueOf(Paths.get("resources/img1.jpg")));

    ImgPanel imgPanel = new ImgPanel(imageHelper.getImage());
    SettingsPanel settingsPanel = new SettingsPanel();

    Container content = frame.getContentPane();
    content.add(imgPanel);

    content.add(settingsPanel, BorderLayout.SOUTH);

    frame.setSize((int) (imageHelper.getWidth() * 1.25), (int) (imageHelper.getHeight() * 1.25));

    WaveAnimation wa = new WaveAnimation(imgPanel, imageHelper);
    wa.start();
  }

  public static void startAnimation() {}
}
