package com.wr.common.customize.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @Author: RainCity
 * @Date: 2021-09-14 20:51:49
 * @Desc: Base64 转 MultipartFile
 */
public class Base64File implements MultipartFile {
    public static final Logger log = LoggerFactory.getLogger(Base64File.class);
    private final byte[] imgContent;
    private final String header;

    public Base64File(byte[] imgContent, String header) {
        this.imgContent = imgContent;
        this.header = header.split(";")[0];
    }

    /**
     * Base64 转 MultipartFile
     * @param prefix base64 文件字符串前缀 如：data:image/jpg;base64
     * @param base64 base64 文件字符串
     * @return org.springframework.web.multipart.MultipartFile
     */
    public static MultipartFile base64ToMultipart(String prefix,String base64) {
        byte[] b = Base64.decodeBase64(base64);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        return new Base64File(b, prefix);
    }

    @Override
    public String getName() {
        return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
    }

    @Override
    public String getOriginalFilename() {
        return System.currentTimeMillis() + (int) (Math.random() * 10000) + "." + header.split("/")[1];
    }

    @Override
    public String getContentType() {
        return header.split(":")[1];
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File file) throws IOException, IllegalStateException {
        new FileOutputStream(file).write(imgContent);
    }

}
