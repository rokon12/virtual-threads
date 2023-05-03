package ca.bazlur;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import static ca.bazlur.Utils.readPoolName;

public class TenMillionThreads {

//Ref: https://youtu.be/UVoGE0GZZPI?t=832

    public static void main(String[] args) throws InterruptedException {
        var poolNames = ConcurrentHashMap.<String>newKeySet();
        var pThreadNames = ConcurrentHashMap.<String>newKeySet();

        var threads = IntStream.range(0, 10_000_000)
                .mapToObj(i -> Thread.ofVirtual().unstarted(() -> {
                    poolNames.add(readPoolName());
                    pThreadNames.add(Utils.readWorkerName());
                })).toList();

        var begin = Instant.now();
        threads.forEach(Thread::start);

        for (var thread : threads) {
            thread.join();
        }

        var end = Instant.now();
        System.out.println("Time taken: " + (end.toEpochMilli() - begin.toEpochMilli()) + "ms");
        System.out.println("# Number of cores: " + Runtime.getRuntime().availableProcessors());
        System.out.println("# Number of pools: " + poolNames.size());
        System.out.println("# Number of Platform Threads: " + pThreadNames.size());
    }
}
