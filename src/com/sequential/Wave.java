package com.sequential;

public class Wave {

  public Wave() {}

  public static int waveFunction(double x, int c, double angel, String corner) {
    double tan = Math.tan(Math.toRadians(angel));
    int result = 0;
    switch (corner) {
      case "top-left":
        result = (int) (-x * tan + c);
        break;
      case "top-right":
        result = (int) (x * tan + c);
        break;
      case "bottom-left":
        result = (int) (x * tan + c);
        break;
      case "bottom-right":
        result = (int) (-x * tan + c);
        break;
    }

    return result;
  }
}
