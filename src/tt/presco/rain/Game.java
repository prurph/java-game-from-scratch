package tt.presco.rain;

public class Game implements Runnable {

    public static int width = 300;
    public static int height = width / 16 * 9;
    public static int scale = 3;

    private Thread thread;
    private boolean running = false;

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

        }
    }

}
