package ca.bazlur;

import java.util.concurrent.*;
import java.util.List;

public class ThroughputComparison {
    private static final int TASK_COUNT = 10000;
    private static final int EXECUTOR_THREADS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws Exception {
        System.out.println("Throughput comparison using traditional ExecutorService:");
        long startTime = System.nanoTime();
        throughputTest(() -> Executors.newFixedThreadPool(EXECUTOR_THREADS));
        long endTime = System.nanoTime();
        long traditionalExecutorTime = endTime - startTime;

        System.out.println("Traditional ExecutorService time: " + TimeUnit.NANOSECONDS.toMillis(traditionalExecutorTime) + " ms");

        System.out.println("Throughput comparison using virtual threads:");
        startTime = System.nanoTime();
        throughputTest(Executors::newVirtualThreadPerTaskExecutor);
        endTime = System.nanoTime();
        long virtualThreadsTime = endTime - startTime;

        System.out.println("Virtual threads time: " + TimeUnit.NANOSECONDS.toMillis(virtualThreadsTime) + " ms");
        double improvementPercentage = ((traditionalExecutorTime - virtualThreadsTime) / (double) traditionalExecutorTime) * 100;
        System.out.println("Throughput improvement using virtual threads: " + improvementPercentage + "%");
    }

    private static void throughputTest(Callable<ExecutorService> executorSupplier) throws Exception {
        LinkedBlockingQueue<Future<?>> futures = new LinkedBlockingQueue<>();

        try (ExecutorService executorService = executorSupplier.call()) {

            for (int i = 0; i < TASK_COUNT; i++) {
                Future<?> future = executorService.submit(ThroughputComparison::dummyServiceCall);
                futures.add(future);
            }

            while (!futures.isEmpty()) {
                futures.poll().get();
            }

            executorService.shutdown();

            if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                System.out.println("Executor did not terminate in the specified time.");
                List<Runnable> droppedTasks = executorService.shutdownNow();
                System.out.println("Executor was abruptly shut down. "
                        + droppedTasks.size() + " tasks will not be executed.");
            }
        }
    }

    private static void dummyServiceCall() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Task interrupted: " + e.getMessage());
        }
    }
}