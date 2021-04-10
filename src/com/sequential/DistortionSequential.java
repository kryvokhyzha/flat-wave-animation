package com.sequential;

import com.utils.ImageHelper;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class DistortionSequential {
  public static BufferedImage distorImage(
      int[][][] array3d, int width, int height, ArrayList<int[]> points) {
    Random random = new Random();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        boolean skipPoint = true;

        for (int[] point : points) {
          if (Math.pow(y - point[1], 2) + Math.pow(x - point[0], 2)
              < WaveAnimationSequential.distortionRadiusValueSquare) {
            skipPoint = false;
            break;
          }
        }

        if (skipPoint) {
          continue;
        }

        int x_ =
            (int)
                (Math.cos(x + y) * Math.sqrt(WaveAnimationSequential.distortionRadiusValueSquare)
                    + x);
        int y_ =
            (int)
                (random.nextGaussian()
                        * Math.sqrt(WaveAnimationSequential.distortionRadiusValueSquare)
                        / 2
                    + y);
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
