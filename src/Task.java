public class Task {

    static final Object monitor = new Object();
    static volatile int current = 0;

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                while (true) {
                    synchronized (monitor) {
                        while (current == 1) {
                            monitor.wait();
                        }
                        Thread.sleep(500);
                        System.out.println("ping");
                        current = 1;
                        monitor.notify();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                while (true) {
                    synchronized (monitor) {
                        while (current == 0) {
                            monitor.wait();
                        }
                        Thread.sleep(500);
                        System.out.println("pong");
                        current = 0;
                        monitor.notify();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
