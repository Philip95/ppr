import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {
    public static final int MAX_ITERATIONS = 1000;

    public static void main(String[] args) throws Exception {
        int width = 1920;
        int height = 1080;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int black = 0x000000, white = 0xFFFFFF;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                double c_re = (col - width/2)*4.0/width;
                double c_im = (row - height/2)*4.0/width;

                double x = 0;
                double y = 0;

                int currentIteration = 0;

                while (x*x+y*y < 4 && currentIteration < MAX_ITERATIONS) {
                    double x_new = x*x-y*y+c_re;

                    y = 2*x*y+c_im;
                    x = x_new;

                    currentIteration++;
                }

                if (currentIteration < MAX_ITERATIONS) {
                    image.setRGB(col, row, white);
                } else {
                    image.setRGB(col, row, black);
                }
            }
        }
        ImageIO.write(image, "png", new File("mandelbrot.png"));
    }
}