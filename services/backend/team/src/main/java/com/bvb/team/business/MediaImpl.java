package com.bvb.team.business;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobStorageException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaImpl implements MediaService {
    @Value("${spring.cloud.azure.storage.blob.connection-string}")
    private String azureStorageConnectionString;

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;

    @Transactional
    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        String blobName = UUID.randomUUID().toString();
        BlobContainerClient containerClient = new BlobServiceClientBuilder()
                .connectionString(azureStorageConnectionString)
                .buildClient()
                .getBlobContainerClient(containerName);

        try (InputStream dataStream = file.getInputStream()) {
            containerClient.getBlobClient(blobName).upload(dataStream, file.getSize());
        } catch (BlobStorageException ex) {
            throw new RuntimeException("Error uploading blob: " + ex.getMessage());
        } catch (IOException ex) {
            throw new RuntimeException("Error uploading file: " + ex.getMessage());
        }

        return blobName;
    }

    @Override
    public void deleteImage(String blobName) throws IOException {
        BlobContainerClient containerClient = new BlobServiceClientBuilder()
                .connectionString(azureStorageConnectionString)
                .buildClient()
                .getBlobContainerClient(containerName);

        try {
            containerClient.getBlobClient(blobName).delete();
        } catch (Exception e) {
            throw new RuntimeException("Error deleting blob: " + e.getMessage());
        }
    }
}
