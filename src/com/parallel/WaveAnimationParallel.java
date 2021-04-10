package com.parallel;

import com.components.GeneralFrame;
import com.components.ScoreLabel;
import com.utils.CornerPosition;
import com.utils.ImageHelper;
import com.utils.WaveFunction;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.*;

public class WaveAnimationParallel extends Thread {
  private final GeneralFrame frame;
  private final JLabel jLabel;
  private final ImageHelper imageHelper;
  private final ScoreLabel scoreLabel;
  private final HashMap<CornerPosition, int[]> cornerPixels;

  public static int delayValue, distortionRadiusValueSquare;

  public static final AtomicBoolean stopAnimationFlag = new AtomicBoolean(true);
  private boolean isPassFinish = false;

  public WaveAnimationParallel(
      GeneralFrame frame, JLabel jLabel, ImageHelper imageHelper, ScoreLabel scoreLabel) {
    this.frame = frame;
    this.jLabel = jLabel;
    this.imageHelper = imageHelper;
    this.scoreLabel = scoreLabel;

    this.cornerPixels = new HashMap<>();

    this.cornerPixels.put(CornerPosition.TOPLEFT, new int[] {0, 0});
    this.cornerPixels.put(CornerPosition.TOPRIGHT, new int[] {imageHelper.getWidth() - 1, 0});
    this.cornerPixels.put(CornerPosition.BOTTOMLEFT, new int[] {0, imageHelper.getHeight() - 1});
    this.cornerPixels.put(
        CornerPosition.BOTTOMRIGHT,
        new int[] {imageHelper.getWidth() - 1, imageHelper.getHeight() - 1});
  }

  private HashMap<CornerPosition, int[]> getRandomCornerPixel() {
    short index = (short) Math.round(Math.random() * 3);

    CornerPosition key = this.cornerPixels.keySet().toArray(new CornerPosition[0])[index];
    int[] value = this.cornerPixels.get(key);

    HashMap<CornerPosition, int[]> result = new HashMap<>();
    result.put(key, value);
    return result;
  }

  private ArrayList<int[]> buildWaveFunctionKeyPoints(int c, double angel, CornerPosition corner) {
    ArrayList<int[]> points = new ArrayList<>();
    for (double x = 0; x < this.imageHelper.getWidth(); x += 7) {
      int yCurrent = WaveFunction.getLinearValue(x, c, angel, corner);
      int xCurrent = (int) Math.round(x);
      if (yCurrent < 0
          || yCurrent >= this.imageHelper.getHeight()
          || xCurrent < 0
          || xCurrent >= this.imageHelper.getWidth()) continue;

      this.isPassFinish = false;

      points.add(new int[] {xCurrent, yCurrent});
    }

    return points;
  }

  public void startAnimation() {
    int c = 0;
    double angel = 45;
    CornerPosition corner = CornerPosition.TOPLEFT;
    int[] pixels = this.cornerPixels.get(corner);

    while (!WaveAnimationParallel.stopAnimationFlag.get()) {
      this.isPassFinish = false;
      // Pass loop
      double tan = Math.tan(Math.toRadians(angel));

      switch (corner) {
        case TOPLEFT:
        case BOTTOMRIGHT:
          c = (int) (pixels[1] + pixels[0] * tan);
          break;
        case TOPRIGHT:
        case BOTTOMLEFT:
          c = (int) (pixels[1] - pixels[0] * tan);
          break;
      }

      this.scoreLabel.setCorner(corner.getPosition());
      this.scoreLabel.setAngel(angel);
      this.scoreLabel.increaseScore();

      int[][][] originalImage3d = this.imageHelper.get3dArray();
      while (!this.isPassFinish && !WaveAnimationParallel.stopAnimationFlag.get()) {
        int[][][] img3d =
            ImageHelper.copyOfRGBArray(
                originalImage3d, this.imageHelper.getWidth(), this.imageHelper.getHeight());

        isPassFinish = true;

        ArrayList<int[]> points = buildWaveFunctionKeyPoints(c, angel, corner);

        BufferedImage distorImg =
            DistortionParallel.distorImage(
                img3d, this.imageHelper.getWidth(), this.imageHelper.getHeight(), points);

        this.jLabel.setIcon(new ImageIcon(distorImg));
        this.frame.updateCanvas();

        switch (corner) {
          case TOPLEFT:
          case TOPRIGHT:
            c += 1;
            break;
          case BOTTOMLEFT:
          case BOTTOMRIGHT:
            c -= 1;
            break;
        }

        try {
          Thread.sleep(delayValue);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      angel = Math.random() * 360;
      HashMap<CornerPosition, int[]> randomCorner = getRandomCornerPixel();

      corner = randomCorner.keySet().toArray(new CornerPosition[0])[0];
      pixels = randomCorner.get(corner);
    }

    stopAnimation();
  }

  public void stopAnimation() {
    this.scoreLabel.setCorner(CornerPosition.TOPLEFT.getPosition());
    this.scoreLabel.setAngel(45);
    this.scoreLabel.setScore(0);
    this.jLabel.setIcon(new ImageIcon(this.imageHelper.getImage()));
    this.frame.updateCanvas();
  }

  @Override
  public void run() {
    startAnimation();
  }
}
