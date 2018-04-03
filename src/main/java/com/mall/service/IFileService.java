package com.mall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author dhf
 */
public interface IFileService {
    String upload(MultipartFile file, String path);


}
