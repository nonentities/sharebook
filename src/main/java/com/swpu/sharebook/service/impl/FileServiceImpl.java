package com.swpu.sharebook.service.impl;

import com.swpu.sharebook.entity.User;
import com.swpu.sharebook.mapper.BookMapper;
import com.swpu.sharebook.mapper.UserMapper;
import com.swpu.sharebook.service.FileService;
import com.swpu.sharebook.shiro.util.UserUtil;
import com.swpu.sharebook.util.returnvalue.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.basepath}")
    private String basePath;

    @Value("${file.baseurl}")
    private String fileBaseUrl;


    @Autowired
    private UserMapper userMapper;


    @Override
    public ResponseResult uploadAvatar(MultipartFile file) {


        // 获取文件名
        String fileName = file.getOriginalFilename();

        String realFileName = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));

        // 将当前时间毫秒数作为文件名
        String filePath = basePath + realFileName;


        // 网络访问路径
        String avatarUrl = fileBaseUrl+ realFileName;


        try {

            // 将文件写入对应位置
            file.transferTo(new File(filePath));
            // 修改用户数据
            User user = userMapper.selectById(UserUtil.getUserId());
            user.setHeadPortrait(avatarUrl);
            userMapper.updateById(user);


        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传错误!");
        }





        return ResponseResult.SUCCESS("文件上传成功",avatarUrl);
    }

    @Override
    public ResponseResult uploadBookPicture(MultipartFile file) throws IOException {

        // 获取文件名
        String fileName = file.getOriginalFilename();

        String realFileName = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));

        // 将当前时间毫秒数作为文件名
        String filePath = basePath + realFileName;


        // 网络访问路径
        String bookUrl = fileBaseUrl+ realFileName;


        try {

            // 将文件写入对应位置
            file.transferTo(new File(filePath));
            // 修改用户数据



        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传错误!");
        }


        return ResponseResult.SUCCESS("文件上传成功",bookUrl);
    }
}
