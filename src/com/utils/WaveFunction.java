package com.utils;

public class WaveFunction {

  public WaveFunction() {}

  public static int getLinearValue(double x, int c, double k) {
    return (int) (-x * k + c);
  }
}
