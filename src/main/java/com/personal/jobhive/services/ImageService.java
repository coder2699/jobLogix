package com.personal.jobhive.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadImage(MultipartFile companyImage, String filename);

    String getUrlFromPublicId(String publicId);

}