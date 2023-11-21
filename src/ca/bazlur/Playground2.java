package ca.bazlur;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Playground2 {
    public static void main(String[] args) throws InterruptedException {

        //
        Thread thread = Thread.ofVirtual().start(() -> {
            System.out.println("Hello from " + Thread.currentThread());

        });

        Thread unstarted = Thread.ofVirtual().unstarted(() -> {
        });

        unstarted.start();


        Thread.startVirtualThread(() -> {

        });


        ExecutorService myPool = Executors.newFixedThreadPool(10);

        //






        // We don't pool virtual threads
        // short-lived
        // task per basis


        // Traditional threads -> non-heap
        // virtual threads  -> heap


        //F/J ->
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("processors = " + processors);

        try (var pool = Executors.newVirtualThreadPerTaskExecutor()){
            pool.submit(() -> doSomeAmazingWork());

        }

        // F/J 
        // CT  <- VT  ( keep on running)
        // an opportunity to block itself.
        // I/O opps.
        // sleeps

        // 1000
        //  U -> Server ->
        //        call database -> 200 mls | invoke microservices -> 200 mls
        //
        //





        // virtual threads are deamon threads

        thread.join();
    }

    private static Integer doSomeAmazingWork() {

        return 1;
    }
}
