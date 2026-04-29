package com.yzh.fv.controller;

import com.yzh.fv.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传下载
 *
 * @author 杨振华
 * @since 2023/1/13
 */
/*
@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {

    @Value("${fv.path}")
    private String basePath;

    */
/**
     * 上传
     *
     * @param file 文件
     * @return {@link R}<{@link String}>
     *//*

    @PostMapping("/upload")
    // 参数名必须为file
    */
/*public R<String> upload(MultipartFile file){
        // file是一个临时文件，需要转存到指定的位置，不然就会被删除
        // 原始文件名
        String originalFilename = file.getOriginalFilename();
        // 文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // UUID重新生成文件名，防止文件名重复造成文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;

        // 创建一个目录对象
        File dir = new File(basePath);
        if (!dir.exists()){
            dir.mkdirs();
        }

        try {
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(fileName);
    }*//*

    public R<String> upload(MultipartFile file){
        // 添加文件存在性检查
        if (file.isEmpty()) {
            throw new RuntimeException("上传文件为空");
        }

        // file是一个临时文件，需要转存到指定的位置，不然就会被删除
        // 原始文件名
        String originalFilename = file.getOriginalFilename();

        // 添加原始文件名检查
        if (originalFilename == null || originalFilename.lastIndexOf(".") == -1) {
            throw new RuntimeException("文件名格式不正确");
        }

        // 文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // UUID重新生成文件名，防止文件名重复造成文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;

        // 创建一个目录对象
        File dir = new File(basePath);
        if (!dir.exists()){
            if (!dir.mkdirs()) {
                throw new RuntimeException("创建目录失败: " + basePath);
            }
        }

        try {
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            log.error("文件上传失败: {}", e.getMessage()); // 添加日志记录
            throw new RuntimeException("文件上传失败: " + e.getMessage());
        }
        return R.success(fileName);
    }


    */
    /**
     * 下载
     *
     * @return {@link R}<{@link String}>
     *//*

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        // 输入流读取文件内容
        try {
            FileInputStream inputStream = new FileInputStream(new File(basePath + name));

            // 输出流将文件写回浏览器，浏览器展示
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int length = 0;
            byte[] bytes = new byte[1024];
            while ((length = inputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,length);
                outputStream.flush();
            }
            outputStream.close();
            inputStream.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
*/
@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {

    private final String basePath = System.getProperty("user.dir") + "/image/";

    @PostConstruct
    public void init(){
        log.info("文件上传路径为: {}", basePath);
    }

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){

        if (file.isEmpty()) throw new RuntimeException("上传文件为空");

        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + suffix;

        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败: " + e.getMessage());
        }

        return R.success(fileName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            File file = new File(basePath + name);
            if (!file.exists()) throw new RuntimeException("文件不存在");

            FileInputStream inputStream = new FileInputStream(file);
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");

            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1){
                outputStream.write(buffer,0,len);
            }
            inputStream.close();
            outputStream.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
