import java.util.Random;

class Monitor {
    private boolean ready = false;

    public synchronized void produce() {
        if (ready) {
            return;
        }

        System.out.println("Отправляю событие");



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
                System.out.println("проблемы с потребителем");
                Thread.currentThread().interrupt();

            }
        }

        System.out.println("Получаю событие");

        ready = false;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


public class Main {
    public static void main(String[] args) {
        Monitor monitor = new Monitor();

        Thread producerThread = new Thread(new Producer(monitor));
        Thread consumerThread = new Thread(new Consumer(monitor));

        producerThread.start();
        consumerThread.start();
    }
}


