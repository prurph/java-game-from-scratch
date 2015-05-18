package tt.presco.rain.graphics;

import java.util.Random;

public class Screen {

    private int width, height;
    public int[] pixels;

    public final int MAP_SIZE = 8;
    public final int MAP_SIZE_MASK = MAP_SIZE - 1;
    public int[] tiles = new int[MAP_SIZE * MAP_SIZE];

    private Random random = new Random();

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        // Create array of one integer for each pixel in the screen.
        pixels = new int[width * height];

        for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
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

    public void render(int xOffset, int yOffset) {
        for (int y = 0; y < height; y++) {
            int yy = y + yOffset;
            // if (yy < 0 || yy >= height) break;
            for (int x = 0; x < width; x++) {
                int xx = x + xOffset;
                // if (xx < 0 || xx >= width) break;

                // Tiles will be 16px x 16px. Every 16 x pixels results in a new
                // tile. Every 16 y pixels moves down a "row" (64) of tiles.
                // Instead of x / 16, shift right 4 places (equivalent to
                // dividing by 2 four times). Bitwsise & with MAP_SIZE_MASK is
                // like modding by the number of tiles in a row/column.
                int tileIndex = ((xx >> 4) & MAP_SIZE_MASK) + ((yy >> 4) & MAP_SIZE_MASK) * MAP_SIZE;
                pixels[x + y * width] = tiles[tileIndex]; // 50400 pixels total.
            }
        }
    }
}
