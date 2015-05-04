package tt.presco.rain.graphics;

public class Screen {

    private int width, height;
    public int[] pixels;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        // Create array of one integer for each pixel in the screen.
        pixels = new int[width * height];
    }

    // Reset all pixels to black.
    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    public void render() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[x + y * width] = 0xff00ff;
            }
        }
    }
}
