package com.example.bio.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.Result;
import com.example.bio.common.constant.CommonConstant;
import com.example.bio.model.User;
import com.example.bio.service.FileService;
import com.example.bio.service.UserService;
import com.example.bio.util.ResponseUtil;
import io.minio.errors.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
@Api(value = "用户模块", tags = "用户模块")
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {

    private FileService fileService;

    private UserService userService;

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "上传头像")
    @PostMapping("/uploadAvatar")
    public Result<?> uploadAvatar(@RequestParam MultipartFile file) {
        if (ObjectUtil.isNotNull(file)) {
            fileService.uploadAvatar(file);
            return ok("上传成功");
        } else {
            return fail("未上传文件");
        }
    }

    @ApiOperation("获取头像")
    @GetMapping("/loadAvatar")
    public void loadAvatar(@RequestParam String username) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException {
        if (StrUtil.isNotBlank(username)) {
            User user = userService.getOneByUsername(username);
            InputStream inputStream = fileService.loadAvatar(user.getId(), user.getAvatar());
            getResponse().setContentType("image/png");
            getResponse().setCharacterEncoding("utf-8");
            ServletOutputStream outputStream = getResponse().getOutputStream();
            int len;
            byte[] buf = new byte[CommonConstant.BUFFER_SIZE];
            while ((len = inputStream.read(buf, 0, CommonConstant.BUFFER_SIZE)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } else {
            ResponseUtil.out(getResponse(), ResponseUtil.resultMap(false, 500, "未能获取头像"));
        }
    }
}
