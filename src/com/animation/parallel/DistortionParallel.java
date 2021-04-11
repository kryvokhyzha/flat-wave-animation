package com.animation.parallel;

import com.animation.WaveAnimation;
import com.utils.ImageHelper;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class DistortionParallel {
  public static BufferedImage distorImage(
      int[][][] array3d, int width, int height, ArrayList<int[]> points) {
    ArrayList<DistortionStageForkJoin> tasks = new ArrayList<>();

    int minX = points.stream().mapToInt(a -> a[0]).min().orElseThrow(NoSuchElementException::new);
    int minY = points.stream().mapToInt(a -> a[1]).min().orElseThrow(NoSuchElementException::new);
    int maxX = points.stream().mapToInt(a -> a[0]).max().orElseThrow(NoSuchElementException::new);
    int maxY = points.stream().mapToInt(a -> a[1]).max().orElseThrow(NoSuchElementException::new);

    minX = Math.max(0, minX - WaveAnimation.distortionRadiusValue);
    minY = Math.max(0, minY - WaveAnimation.distortionRadiusValue);

    maxX = Math.min(width, maxX + WaveAnimation.distortionRadiusValue + 1);
    maxY = Math.min(height, maxY + WaveAnimation.distortionRadiusValue + 1);

    for (int x = minX; x < maxX; x++) {
      DistortionStageForkJoin task =
          new DistortionStageForkJoin(x, points, minY, maxY, width, height);
      tasks.add(task);
      task.fork();
    }

    for (DistortionStageForkJoin task : tasks) {
      ArrayList<int[]> result = task.join();

      for (int[] point : result) {
        array3d[point[0]][point[1]][0] = array3d[point[2]][point[3]][0];
        array3d[point[0]][point[1]][1] = array3d[point[2]][point[3]][1];
        array3d[point[0]][point[1]][2] = array3d[point[2]][point[3]][2];
      }
    }

    return ImageHelper.array3dToImage(array3d, width, height);
  }
}
