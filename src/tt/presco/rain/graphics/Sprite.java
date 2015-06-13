package tt.presco.rain.graphics;

public class Sprite {

    private final int SIZE;
    private int x, y;
    public int[] pixels;
    private SpriteSheet sheet;

    public static Sprite grass = new Sprite(16, 0, 0, null);

    public Sprite(int size, int x, int y, SpriteSheet sheet) {
        SIZE = size;
        // Create new pixel array the size of the sprite.
        pixels = new int[SIZE * SIZE];
        // Set location of target sprite in sprite sheet (how many "boxes" over and down it is in the sheet).
        this.x = x * size;
        this.y = y * size;
        this.sheet = sheet;
        load();
    }

    private void load() {
        // Set this sprite's pixel array from the sprite sheet.
       for (int y = 0; y < SIZE; y++) {
           for (int x = 0; x < SIZE; x++) {
               pixels[x + y * SIZE] = sheet.pixels[(this.x + x) + (this.y + y) * sheet.SIZE];
           }
       }
    }
}
