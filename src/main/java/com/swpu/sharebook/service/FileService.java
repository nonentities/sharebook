package com.swpu.sharebook.service;

import com.swpu.sharebook.util.returnvalue.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    ResponseResult uploadAvatar(MultipartFile file) throws IOException;
    ResponseResult uploadBookPicture(MultipartFile file) throws IOException;
}
