package ca.bazlur;

import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingStream;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

//-XX:+UnlockDiagnosticVMOptions

public class PinnedExample {

    private static final Object lock = new Object();
    private static int counter = 0;

    public static void main(String[] args) throws InterruptedException {

        try (var rs = new RecordingStream()){
            rs.enable("jdk.VirtualThreadPinned");
            rs.onEvent("jdk.VirtualThreadPinned", System.out::println);
            rs.startAsync();
            executePinnedExample();


            Thread.sleep(1000);

            rs.dump(Path.of("pinned.jfr"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void executePinnedExample() throws InterruptedException {
        ChronoUnit delay = ChronoUnit.MICROS;
        var pThreadNames = ConcurrentHashMap.<String>newKeySet();
        var threads = IntStream.range(0, 100)
                .mapToObj(index -> Thread.ofVirtual().unstarted(() -> {
                    try {
                        if (index == 0) {
                            System.out.println(Thread.currentThread());
                        }
                        pThreadNames.add(Utils.readWorkerName());

                    synchronized (lock) {
                        Thread.sleep(Duration.of(1, delay));
                        counter++;
                    }

                        if (index == 0) {
                            System.out.println(Thread.currentThread());
                        }
                        pThreadNames.add(Utils.readWorkerName());

                        synchronized (lock) {
                            Thread.sleep(Duration.of(1, delay));
                            counter++;
                        }

                        if (index == 0) {
                            System.out.println(Thread.currentThread());
                        }
                        pThreadNames.add(Utils.readWorkerName());

                        synchronized (lock) {
                            Thread.sleep(Duration.of(1, delay));
                            counter++;
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
