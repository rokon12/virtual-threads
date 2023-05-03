public class Main {
	public static void main(String[] args) throws InterruptedException {
		var thread = new Thread(() -> {
			System.out.println("Hello world!");
			sleep();
		});
		thread.start();
		thread.join();
	}

	private static void sleep() {
		try {
			Thread.sleep(1000*1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}