package com.wr.file.service;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.wr.common.core.utils.file.FileTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * FastDFS 文件存储
 * 
 * @author wr
 */
@Service
public class FastDfsSysFileServiceImpl implements ISysFileService
{
    /**
     * 域名或本机访问地址
     */
    @Value("${fdfs.domain}")
    public String domain;

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * FastDfs文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     * @throws Exception
     */
    @Override
    public String uploadFile(MultipartFile file) throws Exception
    {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
                FileTypeUtils.getExtension(file), null);
        return domain + "/" + storePath.getFullPath();
    }

    @Override
    public String uploadPemFile(MultipartFile file) throws Exception {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
                FileTypeUtils.getExtension(file), null);
        return domain + "/pem/" + storePath.getFullPath();
    }
}
