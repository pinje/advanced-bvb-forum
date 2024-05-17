package com.bvb.tournament.business;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MediaService {
    String uploadImage(MultipartFile file) throws IOException;
    void deleteImage(String imageId) throws IOException;
}
