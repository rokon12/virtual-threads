package ca.bazlur;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class LimitVirtualThreadExample {

    private Semaphore semaphore = new Semaphore(10);

    public static void main(String[] args) throws InterruptedException {
        var limit = new LimitVirtualThreadExample();

        var executorService = Executors.newVirtualThreadPerTaskExecutor();

        var latch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> {
                try {
                    limit.doSomething();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
        }

        latch.await();

        //executorService.shutdown();

    }


    public void doSomething() throws InterruptedException {
        semaphore.acquire();

        try {
            // do something
            System.out.println("Hello world!");

            Thread.sleep(10);
        } finally {
            semaphore.release();
        }
    }

}
