package tt.presco.rain;

public class Game implements Runnable {

    public static int width = 300;
    public static int height = width / 16 * 9;
    public static int scale = 3;

    private Thread thread;

    public synchronized void start() {
        // this is the Game class. It must implement Runnable to allow this.
        thread = new Thread(this, "Display");
        thread.start();
    }

    /**
     * Stops the Game by waiting for the thread to die.
     */
    public synchronized void stop() {
        try {
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
