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
public class FileServiceImpl implements FileService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FileServiceImpl.class);


    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final java.util.Set<String> ALLOWED_EXTENSIONS = new java.util.HashSet<>(
            java.util.Arrays.asList(".jpg", ".jpeg", ".png", ".gif", ".webp", ".bmp")
    );

    @Autowired
    private UserService userService;

    @Autowired
    private UserCacheService cacheService;

    @Autowired
    private MinIoUtils minIoUtils;

    @Override
    public void uploadAvatar(MultipartFile file) {
        // 文件大小校验
        if (file.getSize() > MAX_FILE_SIZE) {
            Asserts.fail("文件大小不能超过5MB");
        }
        // 文件类型校验
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            Asserts.fail("文件名不能为空");
        }
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(extName)) {
            Asserts.fail("不支持的文件类型，仅允许上传图片（jpg/jpeg/png/gif/webp/bmp）");
        }

        String fileName = minIoUtils.renamePic(originalFilename);
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
