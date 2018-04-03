package com.mall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 附件上传service接口
 * @author dhf
 */
public interface IFileService {
    String upload(MultipartFile file, String path);


}
