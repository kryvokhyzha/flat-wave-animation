package com.sequential;

import com.components.GeneralFrame;
import com.components.ScoreLabel;
import com.utils.ImageHelper;
import java.util.HashMap;
import javax.swing.*;

public class WaveAnimation extends Thread {
  private final GeneralFrame frame;
  private final JLabel jLabel;
  private final ImageHelper imageHelper;
  private final ScoreLabel scoreLabel;
  private final HashMap<String, int[]> cornerPixels;
  private boolean isPassFinish = false;

  public WaveAnimation(
      GeneralFrame frame, JLabel jLabel, ImageHelper imageHelper, ScoreLabel scoreLabel) {
    this.frame = frame;
    this.jLabel = jLabel;
    this.imageHelper = imageHelper;
    this.scoreLabel = scoreLabel;

    this.cornerPixels = new HashMap<>();

    this.cornerPixels.put("top-left", new int[] {0, 0});
    this.cornerPixels.put("top-right", new int[] {imageHelper.getWidth() - 1, 0});
    this.cornerPixels.put("bottom-left", new int[] {0, imageHelper.getHeight() - 1});
    this.cornerPixels.put(
        "bottom-right", new int[] {imageHelper.getWidth() - 1, imageHelper.getHeight() - 1});
  }

  private HashMap<String, int[]> getRandomCornerPixel() {
    short index = (short) Math.round(Math.random() * 3);

    String key = this.cornerPixels.keySet().toArray(new String[0])[index];
    int[] value = this.cornerPixels.get(key);

    HashMap<String, int[]> result = new HashMap<>();
    result.put(key, value);
    return result;
  }

  public void startAnimation() {
    int c = 0;
    double angel = 45;
    String corner = "top-left";
    int[] pixels = this.cornerPixels.get(corner);

    while (!StopAnimationFlag.stopAnimationFlag.get()) {
      this.isPassFinish = false;
      // Pass loop
      double tan = Math.tan(Math.toRadians(angel));
      switch (corner) {
        case "top-left":
          c = (int) (pixels[1] + pixels[0] * tan);
          break;
        case "top-right":
          c = (int) (pixels[1] - pixels[0] * tan);
          break;
        case "bottom-left":
          c = (int) (pixels[1] - pixels[0] * tan);
          break;
        case "bottom-right":
          c = (int) (pixels[1] + pixels[0] * tan);
          break;
      }

      this.scoreLabel.setCorner(corner);
      this.scoreLabel.setAngel(angel);
      this.scoreLabel.increaseScore();

      while (!this.isPassFinish && !StopAnimationFlag.stopAnimationFlag.get()) {
        int[][][] img3d = imageHelper.get3dArray();
        this.isPassFinish = true;

        for (double x = 0; x < imageHelper.getWidth(); x += 0.2) {
          int yCurrent = Wave.waveFunction(x, c, angel, corner);
          int xCurrent = (int) Math.round(x);
          if (yCurrent < 0
              || yCurrent >= imageHelper.getHeight()
              || xCurrent < 0
              || xCurrent >= imageHelper.getWidth()) continue;

          this.isPassFinish = false;

          img3d[xCurrent][yCurrent][0] = 255;
          img3d[xCurrent][yCurrent][1] = 0;
          img3d[xCurrent][yCurrent][2] = 0;
        }

        jLabel.setIcon(
            new ImageIcon(
                ImageHelper.array3dToImage(
                    img3d, imageHelper.getWidth(), imageHelper.getHeight())));
        frame.updateCanvas();

        switch (corner) {
          case "top-left":
            c += 1;
            break;
          case "top-right":
            c += 1;
            break;
          case "bottom-left":
            c -= 1;
            break;
          case "bottom-right":
            c -= 1;
            break;
        }
      }
      System.out.println("Finish pass!\n");

      angel = Math.random() * 360;
      HashMap<String, int[]> randomCorner = getRandomCornerPixel();

      corner = randomCorner.keySet().toArray(new String[0])[0];
      pixels = randomCorner.get(corner);
    }

    stopAnimation();
  }

  public void stopAnimation() {
    System.out.println("Animation is stopped!");
    this.scoreLabel.setScore(0);
    this.scoreLabel.setCorner("top-left");
    this.scoreLabel.setAngel(45);
    this.jLabel.setIcon(new ImageIcon(this.imageHelper.getImage()));
    this.frame.updateCanvas();
  }

  public void setPassFinish(boolean passFinish) {
    isPassFinish = passFinish;
  }

  @Override
  public void run() {
    System.out.println(Thread.currentThread().getName());
    startAnimation();
  }
}
