import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        File file = new File(".");
        File[] files = file.listFiles();
        if (files != null) {
            for (File dir : files) {
                if(dir.isDirectory() && dir.getName().startsWith("test")) {
                    BufferedImage source = ImageIO.read(new File(dir, "input.png"));
                    if (source != null) {
                        BufferedImage bufferedImage = cutImage(source);
                        ImageIO.write(bufferedImage, "png", new File(dir, "output.png"));
                    }
                }
            }
        }
    }

    private static BufferedImage cutImage(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int m = width / 64;
        BufferedImage result = new BufferedImage(10 * m, 10 * m, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(y >= 8 * m && y < 16 * m) {
                    int color = bufferedImage.getRGB(x, y);
                    int x1 = -1;
                    int y1 = 1 + y - 8 * m;
                    if(x >= 8 * m && x < 16 * m)
                        x1 = 1 + x - 8 * m;
                    else if(x >= 40 * m && x < 48 * m)
                        x1 = 1 + x - 40 * m;
                    try {
                        if(x1 != -1 && y1 != -1 && ((color >>> 24) & 0xFF) != 0)
                            result.setRGB(x1, y1, color);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.printf("Coordinates: %s %s\n", x1, y1);
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return result;
    }
}
