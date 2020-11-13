package com.example.bio.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.bio.exception.Asserts;
import com.example.bio.model.User;
import com.example.bio.service.FileService;
import com.example.bio.service.UserCacheService;
import com.example.bio.service.UserService;
import com.example.bio.util.MinIoUtils;
import io.minio.PutObjectOptions;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * @author zhangfuqi
 * @date 2020/11/13
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserCacheService cacheService;

    @Autowired
    private MinIoUtils minIoUtils;

    @Override
    public void uploadAvatar(MultipartFile file) {
        String fileName = minIoUtils.renamePic(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            InputStream inputStream = file.getInputStream();
            User user = userService.getCurrentUser();
            String bucketName = user.getId();
            minIoUtils.makeBucket(bucketName);
            // 写入数据库
            UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
            userUpdateWrapper.eq("id", bucketName).set("avatar", fileName);
            userService.update(userUpdateWrapper);
            if (cacheService.getUser(user.getUsername()) != null) {
                cacheService.deleteUserCache(user.getUsername());
            }
            //上传Minio
            PutObjectOptions options = new PutObjectOptions(-1L, 5 * 1024 * 1024);
            minIoUtils.putObject(bucketName, fileName, inputStream, options);
        } catch (Exception e) {
            log.error(e.toString());
            Asserts.fail(e.getMessage());
        }
    }

    @Override
    public InputStream loadAvatar(String userId, String avatarId) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, ErrorResponseException, XmlParserException, InvalidBucketNameException, InsufficientDataException, InternalException {
        return minIoUtils.getObject(userId, avatarId);
    }
}
