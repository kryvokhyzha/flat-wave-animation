package com.sequential;

import com.components.GeneralFrame;
import com.components.ScoreLabel;
import com.utils.ImageHelper;
import com.utils.WaveFunction;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.*;

public class WaveAnimationSequential extends Thread {
  private final GeneralFrame frame;
  private final JLabel jLabel;
  private final ImageHelper imageHelper;
  private final ScoreLabel scoreLabel;
  private final HashMap<String, int[]> cornerPixels;
  private boolean isPassFinish = false;

  public static int delayValue, distortionRadiusValueSquare;

  public static final AtomicBoolean stopAnimationFlag = new AtomicBoolean(true);

  public WaveAnimationSequential(
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

  public BufferedImage distortion(
      int[][][] array3d, int width, int height, ArrayList<int[]> points) {
    Random random = new Random();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        boolean skipPoint = true;

        for (int[] point : points) {
          if (Math.pow(y - point[1], 2) + Math.pow(x - point[0], 2) < distortionRadiusValueSquare) {
            skipPoint = false;
            break;
          }
        }

        int x_ = (int) (Math.cos(x + y) * Math.sqrt(distortionRadiusValueSquare) + x);
        int y_ = (int) (random.nextGaussian() * Math.sqrt(distortionRadiusValueSquare) / 2 + y);
        if (!skipPoint && x_ < width && y_ < height && x_ > 0 && y_ > 0) {
          array3d[x][y][0] = array3d[x_][y_][0];
          array3d[x][y][1] = array3d[x_][y_][1];
          array3d[x][y][2] = array3d[x_][y_][2];
        }
      }
    }

    return ImageHelper.array3dToImage(array3d, width, height);
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

    while (!WaveAnimationSequential.stopAnimationFlag.get()) {
      this.isPassFinish = false;
      // Pass loop
      double tan = Math.tan(Math.toRadians(angel));
      switch (corner) {
        case "top-left":
        case "bottom-right":
          c = (int) (pixels[1] + pixels[0] * tan);
          break;
        case "top-right":
        case "bottom-left":
          c = (int) (pixels[1] - pixels[0] * tan);
          break;
      }

      this.scoreLabel.setCorner(corner);
      this.scoreLabel.setAngel(angel);
      this.scoreLabel.increaseScore();

      while (!this.isPassFinish && !WaveAnimationSequential.stopAnimationFlag.get()) {
        int[][][] img3d = imageHelper.get3dArray();
        this.isPassFinish = true;

        ArrayList<int[]> points = new ArrayList<>();
        for (double x = 0; x < imageHelper.getWidth(); x += 7) {
          int yCurrent = WaveFunction.getLinearValue(x, c, angel, corner);
          int xCurrent = (int) Math.round(x);
          if (yCurrent < 0
              || yCurrent >= imageHelper.getHeight()
              || xCurrent < 0
              || xCurrent >= imageHelper.getWidth()) continue;

          this.isPassFinish = false;

          // try to normalize each color (RGB) in some borders (with some border-width)
          // distortion
          // img3d[xCurrent][yCurrent][0] = 255;
          // img3d[xCurrent][yCurrent][1] = 0;
          // img3d[xCurrent][yCurrent][2] = 0;

          points.add(new int[] {xCurrent, yCurrent});
        }

        BufferedImage blurred =
            distortion(img3d, imageHelper.getWidth(), imageHelper.getHeight(), points);

        jLabel.setIcon(new ImageIcon(blurred));
        frame.updateCanvas();

        switch (corner) {
          case "top-left":
          case "top-right":
            c += 1;
            break;
          case "bottom-left":
          case "bottom-right":
            c -= 1;
            break;
        }

        try {
          Thread.sleep(delayValue);
        } catch (InterruptedException e) {
          e.printStackTrace();
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
    this.scoreLabel.setCorner("top-left");
    this.scoreLabel.setAngel(45);
    this.scoreLabel.setScore(0);
    this.jLabel.setIcon(new ImageIcon(this.imageHelper.getImage()));
    this.frame.updateCanvas();
  }

  public void setPassFinish(boolean passFinish) {
    isPassFinish = passFinish;
  }

  @Override
  public void run() {
    startAnimation();
  }
}
