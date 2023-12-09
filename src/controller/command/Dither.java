package controller.command;

import model.ImageManipulatorModel;

/**
 * The Dither class implements the ImageManipulationCommand interface. It encapsulates
 * all the information necessary to perform a dither operation on an image within
 * the ImageManipulatorModel.
 */
public class Dither implements ImageManipulationCommand{

  private final String srcImageName;
  private final String destImageName;

  /**
   * Constructs a Dither command with the specified source and destination image names.
   *
   * @param srcImageName The name of the source image to which the dither effect will be applied.
   * @param destImageName The name of the destination image where the result will be stored.
   */
  public Dither(String srcImageName, String destImageName) {
    this.srcImageName = srcImageName;
    this.destImageName = destImageName;
  }


  @Override
  public void execute(ImageManipulatorModel m) {
    m.dither(srcImageName, destImageName);
  }
}
