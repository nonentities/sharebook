package com.swpu.sharebook.controller;

import com.swpu.sharebook.service.FileService;
import com.swpu.sharebook.util.returnvalue.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin("*")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseResult avatar(@RequestParam("file") MultipartFile file) throws IOException {
        ResponseResult responseResult = fileService.uploadAvatar(file);

        return responseResult;
    }


    @PostMapping("/book")
    public ResponseResult book(@RequestParam("file") MultipartFile file) throws IOException {
        ResponseResult responseResult = fileService.uploadBookPicture(file);

        return responseResult;
    }



}
