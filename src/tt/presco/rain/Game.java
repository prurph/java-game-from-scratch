package tt.presco.rain;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;

    public static int width = 300;
    public static int height = width / 16 * 9;
    public static int scale = 3;

    private Thread thread;
    private JFrame frame;
    private boolean running = false;

    public Game() {
        Dimension size = new Dimension(width * scale, height * scale);
        // setPreferredSize comes from Game extending Canvas (which extends
        // Component, where the method is defined).
        setPreferredSize(size);

        frame = new JFrame();
    }

    public synchronized void start() {
        running = true;
        // this is the Game class. It must implement Runnable to allow this ref.
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
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (running) {
            update();
            render();
        }
    }

    public void update() {

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
    }

    public static void main(String[] args) {
        Game game = new Game();
        // Immediately make false non-resizable to avoid graphical bugs.
        game.frame.setResizable(false);
        game.frame.setTitle("Rain");
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
