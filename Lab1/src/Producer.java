import java.util.Random;

public class Producer implements Runnable {
    private Monitor monitor;

    public Producer(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (true) {
            Random random = new Random();
            monitor.produce(random.nextInt(100));
        }
    }
}
