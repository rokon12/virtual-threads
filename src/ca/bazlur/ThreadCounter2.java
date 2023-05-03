package ca.bazlur;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class ThreadCounter2 {
    public static void main(String[] args) {
        var counter = new AtomicInteger();

        while (true) {
            Thread.startVirtualThread(() -> {
                counter.incrementAndGet();
                LockSupport.park();
            });
            if (counter.get() % 100_000 == 0) {
                System.out.printf("Number of threads created so far: %d%n", counter.get());
                break;
            }
        }


    }
}
