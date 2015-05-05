package tt.presco.rain.graphics;

public class Screen {

    private int width, height;
    public int[] pixels;
    int xtime, ytime, counter = 0;

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
        counter++;
        if (counter % 10 == 0) {
            xtime++;
        }
        if (counter % 100 == 0) {
            ytime++;
        }
        for (int y = 0; y < height; y++) {
            if (ytime >= height) break;
            for (int x = 0; x < width; x++) {
                if (xtime >= width) break;
                pixels[xtime + ytime * width] = 0xff00ff; // 50400 pixels total
            }
        }
    }
}
