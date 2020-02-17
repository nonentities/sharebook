package com.swpu.sharebook.controller;

import com.swpu.sharebook.util.returnvalue.ResponseResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin("*")
public class FileController {


    @PostMapping("/upload")
    public ResponseResult avatar(@RequestParam("file") MultipartFile file){

//        MultipartFile multipartFile = request.getFile("multipartFile");




        return null;
    }


}
