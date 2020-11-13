package com.example.bio.service;

import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author zhangfuqi
 * @date 2020/11/13
 */
public interface FileService {
    /**
     * 上传用户头像
     *
     * @param file
     */
    void uploadAvatar(MultipartFile file);

    /**
     * 加载头像
     *
     * @param userId
     * @param avatarId
     * @return
     * @throws IOException
     * @throws InvalidResponseException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws ServerException
     * @throws ErrorResponseException
     * @throws XmlParserException
     * @throws InvalidBucketNameException
     * @throws InsufficientDataException
     * @throws InternalException
     */
    InputStream loadAvatar(String userId, String avatarId) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, ErrorResponseException, XmlParserException, InvalidBucketNameException, InsufficientDataException, InternalException;
}
