package com.swpu.sharebook.service;

import com.swpu.sharebook.util.returnvalue.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    ResponseResult uploadAvatar(MultipartFile file);
}
