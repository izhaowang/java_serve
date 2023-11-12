package com.itheima.controller;

import com.itheima.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        String origialFinallyName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + origialFinallyName;
        file.transferTo(new File("C:\\Users\\Adimn\\Desktop\\pdf\\" + fileName));
        return  Result.success("上传成功");
    }
}
