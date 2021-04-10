package com.parallel;

import com.utils.ImageHelper;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DistortionParallel {
  public static BufferedImage distorImage(
      int[][][] array3d, int width, int height, ArrayList<int[]> points) {
    ArrayList<DistortionStageForkJoin> tasks = new ArrayList<>();
    for (int y = 0; y < height; y++) {
      DistortionStageForkJoin task = new DistortionStageForkJoin(y, points, width, height);
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
