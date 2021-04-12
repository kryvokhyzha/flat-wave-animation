package com.animation.parallel;

import com.animation.WaveAnimation;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.RecursiveTask;

public class DistortionStageForkJoin extends RecursiveTask<ArrayList<int[]>> {
  private final int x;
  private final ArrayList<int[]> points;
  private final int minY, maxY;
  private final int width;
  private final int height;
  private static final Random random = new Random();

  public DistortionStageForkJoin(
      int x, ArrayList<int[]> points, int minY, int maxY, int width, int height) {
    this.x = x;
    this.points = points;
    this.minY = minY;
    this.maxY = maxY;
    this.width = width;
    this.height = height;
  }

  @Override
  protected ArrayList<int[]> compute() {
    ArrayList<int[]> result = new ArrayList<>();
    int distortionRadiusValueSquareLocal = WaveAnimation.distortionRadiusValueSquare.get();
    int distortionRadiusValueLocal = WaveAnimation.distortionRadiusValue.get();

    for (int y = minY; y < maxY; y++) {
      boolean skipPoint = true;

      for (int[] point : points) {
        if (Math.pow(y - point[1], 2) + Math.pow(x - point[0], 2)
            < distortionRadiusValueSquareLocal) {
          skipPoint = false;
          break;
        }
      }

      if (skipPoint) {
        continue;
      }

      // add mean offset parameter
      int x_ = (int) (Math.cos(x + y) * distortionRadiusValueLocal + x);
      int y_ = (int) (random.nextGaussian() * distortionRadiusValueLocal / 2 + y);

      if (x_ < width && y_ < height && x_ > 0 && y_ > 0) {
        result.add(new int[] {x, y, x_, y_});
      }
    }
    return result;
  }
}
