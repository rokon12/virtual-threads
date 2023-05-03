package ca.bazlur;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class ThreadCounter {
    public static void main(String[] args) {
        var counter = new AtomicInteger();

        while (true) {
            var thread = new Thread(() -> {
                counter.incrementAndGet();

                if (counter.get() % 100 == 0) {
                    System.out.printf("Number of threads created so far: %d%n", counter.get());
                }
                LockSupport.park();
            });
            thread.start();
        }
    }
}
