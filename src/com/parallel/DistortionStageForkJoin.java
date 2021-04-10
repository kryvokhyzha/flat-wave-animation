package com.parallel;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.RecursiveTask;

public class DistortionStageForkJoin extends RecursiveTask<ArrayList<int[]>> {
  private final int y;
  private final ArrayList<int[]> points;
  private final int width;
  private final int height;
  private static final Random random = new Random();

  public DistortionStageForkJoin(int y, ArrayList<int[]> points, int width, int height) {
    this.y = y;
    this.points = points;
    this.width = width;
    this.height = height;
  }

  @Override
  protected ArrayList<int[]> compute() {
    ArrayList<int[]> result = new ArrayList<>();
    for (int x = 0; x < width; x++) {
      boolean skipPoint = true;

      for (int[] point : points) {
        if (Math.pow(y - point[1], 2) + Math.pow(x - point[0], 2)
            < WaveAnimationParallel.distortionRadiusValueSquare) {
          skipPoint = false;
          break;
        }
      }

      if (skipPoint) {
        continue;
      }

      int x_ =
          (int)
              (Math.cos(x + y) * Math.sqrt(WaveAnimationParallel.distortionRadiusValueSquare) + x);
      int y_ =
          (int)
              (random.nextGaussian()
                      * Math.sqrt(WaveAnimationParallel.distortionRadiusValueSquare)
                      / 2
                  + y);
      if (x_ < width && y_ < height && x_ > 0 && y_ > 0) {
        result.add(new int[] {x, y, x_, y_});
      }
    }
    return result;
  }
}
