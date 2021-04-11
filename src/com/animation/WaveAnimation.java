package com.animation;

import com.animation.parallel.DistortionParallel;
import com.animation.sequential.DistortionSequential;
import com.components.GeneralFrame;
import com.components.ScoreLabel;
import com.utils.CornerPosition;
import com.utils.ImageHelper;
import com.utils.Mode;
import com.utils.WaveFunction;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.*;

public class WaveAnimation extends Thread {
  private final GeneralFrame frame;
  private final JLabel jLabel;
  private final ImageHelper imageHelper;
  private final ScoreLabel scoreLabel;
  private final HashMap<CornerPosition, int[]> cornerPixels;

  private final Mode mode;

  public static int speedValue, delayValue, distortionRadiusValue, distortionRadiusValueSquare;

  public static final AtomicBoolean stopAnimationFlag = new AtomicBoolean(true);
  private boolean isPassFinish = false;

  public WaveAnimation(
      GeneralFrame frame,
      JLabel jLabel,
      ImageHelper imageHelper,
      ScoreLabel scoreLabel,
      Mode mode) {
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

    this.mode = mode;
  }

  private HashMap<CornerPosition, int[]> getRandomCornerPixel() {
    short index = (short) Math.round(Math.random() * 3);

    CornerPosition key = this.cornerPixels.keySet().toArray(new CornerPosition[0])[index];
    int[] value = this.cornerPixels.get(key);

    HashMap<CornerPosition, int[]> result = new HashMap<>();
    result.put(key, value);
    return result;
  }

  private ArrayList<int[]> buildWaveFunctionKeyPoints(
      int c, double tan, double step, CornerPosition corner) {
    ArrayList<int[]> points = new ArrayList<>();

    for (double x = 0; x < this.imageHelper.getWidth(); x += step) {
      int yCurrent = WaveFunction.getLinearValue(x, c, tan, corner);
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

    int[] currentCornerPixels = this.cornerPixels.get(corner);

    int[][][] originalImageArray = this.imageHelper.get3dArray();

    while (!WaveAnimation.stopAnimationFlag.get()) {
      this.isPassFinish = false;
      // Pass loop
      double angelRadians = Math.toRadians(angel);
      double tan = Math.tan(angelRadians);

      int speed = (int) Math.abs(Math.round(tan));

      switch (corner) {
        case TOPLEFT:
        case BOTTOMRIGHT:
          c = (int) (currentCornerPixels[1] + currentCornerPixels[0] * tan);
          break;
        case TOPRIGHT:
        case BOTTOMLEFT:
          c = (int) (currentCornerPixels[1] - currentCornerPixels[0] * tan);
          break;
      }

      this.scoreLabel.setCorner(corner.getPosition());
      this.scoreLabel.setAngel(angel);
      this.scoreLabel.increaseScore();

      while (!this.isPassFinish && !WaveAnimation.stopAnimationFlag.get()) {
        int[][][] distorImageArray =
            ImageHelper.copyOfRGBArray(
                originalImageArray, this.imageHelper.getWidth(), this.imageHelper.getHeight());

        isPassFinish = true;
        double step = Math.max(0.1, Math.abs(distortionRadiusValue * Math.cos(angelRadians)));

        ArrayList<int[]> keyPoints = buildWaveFunctionKeyPoints(c, tan, step, corner);

        if (keyPoints.size() == 0) {
          continue;
        }

        BufferedImage distorImg = null;
        switch (this.mode) {
          case SEQUENTIAL:
            distorImg =
                DistortionSequential.distorImage(
                    distorImageArray,
                    this.imageHelper.getWidth(),
                    this.imageHelper.getHeight(),
                    keyPoints);
            break;
          case PARALLEL:
            distorImg =
                DistortionParallel.distorImage(
                    distorImageArray,
                    this.imageHelper.getWidth(),
                    this.imageHelper.getHeight(),
                    keyPoints);
            break;
        }

        this.jLabel.setIcon(new ImageIcon(distorImg));
        this.frame.updateCanvas();

        switch (corner) {
          case TOPLEFT:
          case TOPRIGHT:
            c += (speedValue + speed);
            break;
          case BOTTOMLEFT:
          case BOTTOMRIGHT:
            c -= (speedValue + speed);
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
      currentCornerPixels = randomCorner.get(corner);
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
