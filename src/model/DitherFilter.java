package model;

/**
 * The DitherFilter class implements the ImageFilter interface and provides an implementation
 * for applying a dithering effect to an image.
 */
public class DitherFilter implements ImageFilter {

  @Override
  public Image applyFilterToImage(Image srcImg) {
    Image ditheredImage = new Image(srcImg.getWidth(), srcImg.getHeight());
    for (int y = 0; y < srcImg.getHeight(); y++) {
      for (int x = 0; x < srcImg.getWidth(); x++) {
        Pixel oldPixel = srcImg.getPixelRGB(x, y);
        int oldColor = oldPixel.getIntensityComponent();
        int newColor = oldColor > 127 ? 255 : 0;
        int error = oldColor - newColor;

        ditheredImage.setPixelRGB(x, y, new Pixel(newColor, newColor, newColor));

        if (x + 1 < srcImg.getWidth()) {
          adjustPixelIntensity(srcImg, x + 1, y, error * 7 / 16.0);
        }
        if (y + 1 < srcImg.getHeight()) {
          if (x > 0) {
            adjustPixelIntensity(srcImg, x - 1, y + 1, error * 3 / 16.0);
          }
          adjustPixelIntensity(srcImg, x, y + 1, error * 5 / 16.0);
          if (x + 1 < srcImg.getWidth()) {
            adjustPixelIntensity(srcImg, x + 1, y + 1, error / 16.0);
          }
        }
      }
    }
    return ditheredImage;
  }

  /**
   * Adjusts the intensity of a pixel at the specified coordinates in the source image.
   * This method modifies the red, green, and blue components of the pixel to the new intensity.
   *
   * @param srcImg The source Image object.
   * @param x The x-coordinate of the pixel.
   * @param y The y-coordinate of the pixel.
   * @param errorFraction The fractional error to be distributed to the pixel.
   */
  private void adjustPixelIntensity(Image srcImg, int x, int y, double errorFraction) {
    Pixel pixel = srcImg.getPixelRGB(x, y);
    int newIntensity = clampValue((int) (pixel.getIntensityComponent() + errorFraction));
    pixel.setRed(newIntensity);
    pixel.setGreen(newIntensity);
    pixel.setBlue(newIntensity);
  }

  /**
   * Clamps the given pixel value to ensure it is within the range of 0 to 255.
   * This method is used to prevent color values from going outside the valid range
   * when adjusting pixel intensities.
   *
   * @param value The pixel value to clamp.
   * @return The clamped pixel value, which will be within the range of 0 to 255.
   */
  private int clampValue(int value) {
    return Math.max(0, Math.min(255, value));
  }
}
