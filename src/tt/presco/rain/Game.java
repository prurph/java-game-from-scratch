package tt.presco.rain;

import tt.presco.rain.graphics.Screen;
import tt.presco.rain.input.Keyboard;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;

    public static int width = 300;
    public static int height = width * 9 / 16;
    public static int scale = 3;
    public static String title = "Rain";

    private Thread thread;
    private JFrame frame;
    private Keyboard key;
    private boolean running = false;

    private Screen screen;

    private BufferedImage image =
            new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    // Get image, get array of pixels that make it up (raster), then get the
    // data buffer for them, and the data it contains. Converts the image data
    // to an array of integers, allowing us to modify the pixels and create an
    // image.
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    public Game() {
        Dimension size = new Dimension(width * scale, height * scale);
        // setPreferredSize comes from Game extending Canvas (which extends
        // Component, where the method is defined).
        setPreferredSize(size);

        screen = new Screen(width, height);
        frame = new JFrame();
        key = new Keyboard();

        frame.addKeyListener(key);
    }

    public synchronized void start() {
        running = true;
        // this is the Game class. It must implement Runnable to allow this ref.
        // Furthermore, when the thread starts, its target's (this') run method
        // is called.
        thread = new Thread(this, "Display");
        thread.start();
    }

    /**
     * Stops the Game by waiting for the thread to die.
     */
    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1.0e9 / 60.0;
        double delta = 0;
        int frames = 0;
        int updates = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frame.setTitle(title + " | " + updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
            }
        }
        stop();
    }

    int x = 0;
    int y = 0;

    public void update() {
        key.update();
        if (key.up) y--;
        if (key.down) y++;
        if (key.left) x++;
        if (key.right) x--;
    }

    public void render() {
        // Retrieve or create the BufferStrategy that extending Canvas provides.
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            // Triple buffering: two back buffers and the front buffer.
            // Allows calculation of the next frame before the "backup" frame is
            // displayed.
            createBufferStrategy(3);
            return;
        }

        screen.clear();
        screen.render(x, y);

        for (int i = 0; i < pixels.length; i++) {
            // Set the BufferedImage's pixels to the screen's pixels.
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        Game game = new Game();
        // Immediately make false non-resizable to avoid graphical bugs.
        game.frame.setResizable(false);
        game.frame.setTitle(Game.title);
        // Since game is subclass of Canvas, it can be added to the frame.
        game.frame.add(game);
        // Set frame to desired size to fit the game that was just added.
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Centers window within the screen.
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);

        game.start();
    }

}
