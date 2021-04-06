package com.sequential;

import com.components.ImgPanel;
import com.utils.ImageHelper;

public class WaveAnimation {
  private final ImgPanel canvas;
  private ImageHelper imageHelper;
  private boolean isFinish = false;

  public WaveAnimation(ImgPanel canvas, ImageHelper imageHelper) {
    this.canvas = canvas;
    this.imageHelper = imageHelper;
  }

  public void start() {
    int c = 0;
    double angel = 15;
    int xCurrent = 0;
    int yCurrent = 0;

    this.isFinish = false;
    while (!this.isFinish) {
      int[][][] img3d = imageHelper.get3dArray();
      this.isFinish = true;
      for (xCurrent = 0; xCurrent < imageHelper.getWidth(); xCurrent++) {
        yCurrent = Wave.waveFunction(xCurrent, c, angel);
        if (yCurrent < 0 || yCurrent >= imageHelper.getHeight()) continue;
        this.isFinish = false;
        img3d[xCurrent][yCurrent][0] = 255;
        img3d[xCurrent][yCurrent][1] = 0;
        img3d[xCurrent][yCurrent][2] = 0;
      }
      canvas.setImage(
          ImageHelper.array3dToImage(img3d, imageHelper.getWidth(), imageHelper.getHeight()));
      canvas.revalidate();
      canvas.repaint();
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      c++;
    }
    System.out.println("Finish!");
  }
}
