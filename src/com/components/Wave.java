package com.components;

public class Wave {
  private final int[] xPoints;
  private final int[] yPoints;
  private final int nPoints;
  private int xStart, yStart;
  private final int width;
  private final int height;

  public Wave(int xStart, int yStart, int width, int height) {
    this.xStart = xStart;
    this.yStart = yStart;

    this.width = width;
    this.height = height;

    this.nPoints = width;

    this.xPoints = new int[nPoints];
    this.yPoints = new int[nPoints];
  }

  public int getnPoints() {
    return nPoints;
  }

  public int[] getxPoints() {
    return xPoints;
  }

  public int[] getyPoints() {
    return yPoints;
  }

  public void setxPoint(int i, int x) {
    this.xPoints[i] = x;
  }

  public void setyPoint(int i, int y) {
    this.yPoints[i] = y;
  }

  public static int waveFunction(int x, int c, double angel) {
    int y = (int) (-x * Math.tan(Math.toRadians(angel)) + c);
    return y;
  }
}
