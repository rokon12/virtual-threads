package ca.bazlur;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class PinnedExampleImproved {


    private static  final ReentrantLock lock = new ReentrantLock();
    private static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        ChronoUnit delay = ChronoUnit.MICROS;
        var pThreadNames = ConcurrentHashMap.<String>newKeySet();

        var threads = IntStream.range(0, 100)
                .mapToObj(index -> Thread.ofVirtual().unstarted(() -> {
                    try {
                        if (index == 0) {
                            System.out.println(Thread.currentThread());
                        }
                        pThreadNames.add(Utils.readWorkerName());

                        lock.lock();
                        try {
                            Thread.sleep(Duration.of(1, delay));
                            counter++;
                        }finally {
                            lock.unlock();
                        }

                        if (index == 0) {
                            System.out.println(Thread.currentThread());
                        }
                        pThreadNames.add(Utils.readWorkerName());

                        lock.lock();
                        try {
                            Thread.sleep(Duration.of(1, delay));
                            counter++;
                        }finally {
                            lock.unlock();
                        }

                        if (index == 0) {
                            System.out.println(Thread.currentThread());
                        }
                        pThreadNames.add(Utils.readWorkerName());

                        lock.lock();
                        try {
                            Thread.sleep(Duration.of(1, delay));
                            counter++;
                        }finally {
                            lock.unlock();
                        }

                        if (index == 0) {
                            System.out.println(Thread.currentThread());
                        }
                        pThreadNames.add(Utils.readWorkerName());

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })).toList();

        threads.forEach(Thread::start);
        for (var thread : threads) {
            thread.join();
        }
        synchronized (lock) {
            System.out.println("counter = " + counter);
        }

        System.out.println("# Number of Platform Threads: " + pThreadNames.size());
    }


}
