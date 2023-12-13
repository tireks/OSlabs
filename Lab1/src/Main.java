import java.util.Random;

class Monitor {
    private boolean ready = false;

    private int[] buffer;

    private int in = 0, out = 0;

    Monitor(int size)
    {
        buffer = new int[size];
    }

    public synchronized void produce(int c) {
        if (ready) {
            return;
        }

        System.out.println("Отправлено сообщение " + c);

        buffer[in] = c;
        in = (in + 1) % buffer.length;

        ready = true;

        notify();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public synchronized void consume() {
        while (!ready) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }

        int c = buffer[out];
        out = (out + 1) % buffer.length;

        System.out.println("Получено сообщение  " + c);

        ready = false;
    }
}


public class Main {
    public static void main(String[] args) {
        Monitor monitor = new Monitor(5);

        Thread producerThread = new Thread(new Producer(monitor));
        Thread consumerThread = new Thread(new Consumer(monitor));

        producerThread.start();
        consumerThread.start();
    }
}


