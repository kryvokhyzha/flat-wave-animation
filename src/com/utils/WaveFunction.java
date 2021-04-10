package com.utils;

public class WaveFunction {

  public WaveFunction() {}

  public static int getLinearValue(double x, int c, double angel, CornerPosition corner) {
    double tan = Math.tan(Math.toRadians(angel));
    int result = 0;

    switch (corner) {
      case TOPLEFT:
      case BOTTOMRIGHT:
        result = (int) (-x * tan + c);
        break;
      case TOPRIGHT:
      case BOTTOMLEFT:
        result = (int) (x * tan + c);
        break;
    }

    return result;
  }

  public static int[] getSquareValue(double x, int c, double angel, String corner) {
    angel = Math.toRadians(angel);
    double y1 = -x * x * 0.001 + c;
    double y2 = x * x * 0.001 + c;

    double x1_n = x * Math.cos(angel) - y1 * Math.sin(angel);
    double y1_n = x * Math.sin(angel) + y1 * Math.cos(angel);

    double x2_n = x * Math.cos(angel) - y2 * Math.sin(angel);
    double y2_n = x * Math.sin(angel) + y2 * Math.cos(angel);

    int[] result = new int[2];

    switch (corner) {
      case "top-left":
      case "bottom-right":
        result[1] = (int) y1_n;
        result[0] = (int) x1_n;
        // result = (int) (-x * tan + c);
        break;
      case "top-right":
      case "bottom-left":
        result[1] = (int) y2_n;
        result[0] = (int) x2_n;
        // result = (int) (x * tan + c);
        break;
    }

    return result;
  }
}
