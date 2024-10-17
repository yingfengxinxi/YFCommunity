package com.wr.common.customize.util;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author: RainCity
 * @Date: 2021-12-30 15:35:27
 * @Desc: 文件压缩
 */
public class ThumbnailsUtils {

    /**
     * 文件压缩
     * @param tempPath 临时文件目录
     * @param multipartFile 要压缩的文件
     * @param width 压缩后的宽
     * @param height 压缩后的高
     * @return java.io.File
     */
    public static File compressFile(String tempPath, MultipartFile multipartFile, int width, int height) throws IOException {
        //确保此路径下有 temp.jpg文件
        File file = new File(tempPath+"/temp.jpg");
        Thumbnails.of(multipartFile.getInputStream()).size(width,height).toFile(file);
        return file;
    }

    /**
     *
     * 文件压缩
     * @param tempPath 临时文件目录
     * @param multipartFile 要压缩的文件
     * @param width 压缩后的宽
     * @param height 压缩后的高
     * @return org.springframework.web.multipart.MultipartFile
     */
    public static MultipartFile compressMultipart(String tempPath, MultipartFile multipartFile, int width, int height) throws IOException {
        File file = compressFile(tempPath,multipartFile,width,height);
        FileInputStream input = new FileInputStream(file);
        return new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
    }
}
