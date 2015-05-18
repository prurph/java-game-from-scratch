package tt.presco.rain.graphics;

import java.util.Random;

public class Screen {

    private int width, height;
    public int[] pixels;

    // Game board consists of 4096 tiles in a 64 tile x 64 tile square.
    public int[] tiles = new int[64 * 64];

    private Random random = new Random();

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        // Create array of one integer for each pixel in the screen.
        pixels = new int[width * height];

        for (int i = 0; i < 64 * 64; i++) {
            // Randomly generate a color from 0 (black) to white.
            tiles[i] = random.nextInt(0xffffff);
        }
    }

    // Reset all pixels to black.
    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    public void render() {
        for (int y = 0; y < height; y++) {
            if (y < 0 || y >= height) break;
            for (int x = 0; x < width; x++) {
                if (x < 0 || x >= width) break;
                // Tiles will be 16px x 16px. Every 16 x pixels results in a new
                // tile. Every 16 y pixels moves down a "row" (64) of tiles.
                // Instead of x / 16, shift right 4 places (equivalent to
                // dividing by 2 four times).
                int tileIndex = (x >> 4) + (y >> 4) * 64;
                pixels[x + y * width] = tiles[tileIndex]; // 50400 pixels total.
            }
        }
    }
}
