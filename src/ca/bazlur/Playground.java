package ca.bazlur;

import java.util.concurrent.Executors;

public class Playground {
    public static void main(String[] args) throws InterruptedException {

        System.setProperty("jdk.traceVirtualThreadLocals", "true");

        var inheritableThreadLocal = new InheritableThreadLocal<String>();

        var thread = Thread.startVirtualThread(() -> {
            System.out.println("Hello world!");
            inheritableThreadLocal.set("Hello world!");
        });

        thread.join();




        var started =Thread.ofVirtual().start(() -> {
            System.out.println("Hello world!");
        });

        var unstarted = Thread.ofVirtual().unstarted(() -> {
            System.out.println("Hello world!");
        });

        var executorService = Executors.newVirtualThreadPerTaskExecutor();
        executorService.submit(() -> {
            var s = inheritableThreadLocal.get();
            System.out.println("s = " + s);
            System.out.println("Hello world!");
        });
    }
}
