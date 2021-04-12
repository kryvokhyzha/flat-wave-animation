package com.animation.sequential;

import com.animation.WaveAnimation;
import com.utils.ImageHelper;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

public class DistortionSequential {
  public static BufferedImage distorImage(
      int[][][] array3d, int width, int height, ArrayList<int[]> points) {
    Random random = new Random();

    int distortionRadiusValueSquareLocal = WaveAnimation.distortionRadiusValueSquare.get();
    int distortionRadiusValueLocal = WaveAnimation.distortionRadiusValue.get();

    int minX = points.stream().mapToInt(a -> a[0]).min().orElseThrow(NoSuchElementException::new);
    int minY = points.stream().mapToInt(a -> a[1]).min().orElseThrow(NoSuchElementException::new);
    int maxX = points.stream().mapToInt(a -> a[0]).max().orElseThrow(NoSuchElementException::new);
    int maxY = points.stream().mapToInt(a -> a[1]).max().orElseThrow(NoSuchElementException::new);

    minX = Math.max(0, minX - distortionRadiusValueLocal);
    minY = Math.max(0, minY - distortionRadiusValueLocal);

    maxX = Math.min(width, maxX + distortionRadiusValueLocal + 1);
    maxY = Math.min(height, maxY + distortionRadiusValueLocal + 1);

    for (int x = minX; x < maxX; x++) {
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

        int x_ = (int) (Math.cos(x + y) * distortionRadiusValueLocal + x);
        int y_ = (int) (random.nextGaussian() * distortionRadiusValueLocal / 2 + y);
        if (x_ < width && y_ < height && x_ > 0 && y_ > 0) {
          array3d[x][y][0] = array3d[x_][y_][0];
          array3d[x][y][1] = array3d[x_][y_][1];
          array3d[x][y][2] = array3d[x_][y_][2];
        }
      }
    }

    return ImageHelper.array3dToImage(array3d, width, height);
  }
}
