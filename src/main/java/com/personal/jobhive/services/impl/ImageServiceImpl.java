package com.personal.jobhive.services.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.personal.jobhive.helpers.AppConstants;
import com.personal.jobhive.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    private Cloudinary cloudinary;

    public ImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile companyImage, String filename) {

        try {
            byte[] data = new byte[companyImage.getInputStream().available()];
            if(data.length!=0) {
                companyImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                    "public_id", filename));

            return this.getUrlFromPublicId(filename);
            }
            return this.getUrlFromPublicId("6d38fe36-c6eb-40dc-8150-1e20042bcb62");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String getUrlFromPublicId(String publicId) {

        return cloudinary
                .url()
                .transformation(
                        new Transformation<>()
                                .width(AppConstants.JOB_IMAGE_WIDTH)
                                .height(AppConstants.JOB_IMAGE_HEIGHT)
                                .crop(AppConstants.JOB_IMAGE_CROP))
                .generate(publicId);

    }

}