package com.kemi.fileupload.controller;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @author: lingk
 * @date: 2021-10-10
 * @description:
 */
@Controller
public class uploadController {

    @RequestMapping("/toUpload")
    public Object toUpload(HttpServletRequest request){
        return "upload";
    }

    @RequestMapping("/upload")
    public Object upload(@RequestParam("file") MultipartFile file, @RequestParam("username") String name){
        System.out.println(name);
        System.out.println(file);

        return saveFile(file);
    }

    @PostMapping("/multiUpload")
    public Object multiUpload(@RequestParam("file") MultipartFile[] files){
        for (MultipartFile file : files){
            saveFile(file);
        }
        return "success";
    }

    private Object saveFile(MultipartFile file) {

        if(file.isEmpty()){
            return "未选择文件";
        }

//        System.out.println("用户目录： " + System.getProperty("user.dir"));
//        System.out.println("classpath: " + ResourceUtils.getURL("classpath: ").getPath());
//        System.out.println("classpath2 : " + ClassUtils.getDefaultClassLoader().getResource("").getPath());

        // 文件名
        String filename = UUID.randomUUID() + file.getOriginalFilename();
        // 文件路径
        String filePath = System.getProperty("user.dir") + "\\file\\";
        // 路径不存在，则创建
        File path = new File(filePath);
        if(!path.exists()){
            path.mkdirs();
        }
        // 完整文件名
        String filePathName = filePath + filename;

        // 创建file
        File localFile = new File(filePathName);

        // 保存
        try{
            file.transferTo(localFile);
            System.out.println(file.getOriginalFilename() + " 上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";
        }

        return "success";
    }

}
