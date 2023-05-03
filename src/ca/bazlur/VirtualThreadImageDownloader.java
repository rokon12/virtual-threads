package ca.bazlur;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class VirtualThreadImageDownloader {
    private static final String BASE_URL = "https://your-image-source.com/";
    private static final String DOWNLOAD_PATH = "images/";
    private static final int IMAGE_COUNT = 10000;

    public static void main(String[] args) throws InterruptedException {
        List<String> imageUrls = new ArrayList<>();
        for (int i = 0; i < IMAGE_COUNT; i++) {
            imageUrls.add(String.format("image-%05d.jpg", i));
        }

        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        HttpClient httpClient = HttpClient.newBuilder()
                .executor(executor)
                .build();

        for (String imageUrl : imageUrls) {
            executor.submit(() -> {
                byte[] imageBytes = downloadImage(httpClient, imageUrl);
                saveImage(imageUrl, imageBytes);
                System.out.println("Downloaded and saved: " + imageUrl);
            });
        }

        executor.shutdown();
        System.out.println("Finished downloading and saving images.");
    }

    private static byte[] downloadImage(HttpClient httpClient, String imageUrl) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + imageUrl))
                .build();
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray()).body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveImage(String fileName, byte[] imageBytes) {
        Path outputPath = Paths.get(DOWNLOAD_PATH, fileName);
        try {
            Files.write(outputPath, imageBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
