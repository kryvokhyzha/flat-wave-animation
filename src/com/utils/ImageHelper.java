package com.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

public class ImageHelper {
  private BufferedImage image;
  private final int width;
  private final int height;

  public ImageHelper(String path) {
    try {
      this.image = ImageIO.read(new File(String.valueOf(Paths.get(path))));
    } catch (IOException e) {
      this.image = new BufferedImage(600, 400, BufferedImage.TYPE_INT_RGB);
      e.printStackTrace();
    }

    this.width = this.image.getWidth();
    this.height = this.image.getHeight();
  }

  public BufferedImage getImage() {
    return image;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  public int[][][] get3dArray() {
    int channels = 3;

    int[][][] data = new int[this.width][this.height][channels];

    for (int x = 0; x < this.width; x++) {
      for (int y = 0; y < this.height; y++) {
        Color c = new Color(this.image.getRGB(x, y));
        int red = (int) (c.getRed());
        int green = (int) (c.getGreen());
        int blue = (int) (c.getBlue());
        data[x][y][0] = red;
        data[x][y][1] = green;
        data[x][y][2] = blue;
      }
    }

    return data;
  }

  public void set3dArray(int[][][] data) {
    BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < this.width; x++) {
      for (int y = 0; y < this.height; y++) {
        Color c = new Color(data[x][y][0], data[x][y][1], data[x][y][2]);
        image.setRGB(x, y, c.getRGB());
      }
    }
    this.image = image;
  }

  public static BufferedImage array3dToImage(int[][][] data, int width, int height) {
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Color c = new Color(data[x][y][0], data[x][y][1], data[x][y][2]);
        image.setRGB(x, y, c.getRGB());
      }
    }
    return image;
  }

  public static int[][][] copyOfRGBArray(int[][][] data, int width, int height) {
    int[][][] result = new int[width][height][3];

    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        result[i][j][0] = data[i][j][0];
        result[i][j][1] = data[i][j][1];
        result[i][j][2] = data[i][j][2];
      }
    }
    return result;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }
}
