package com.example.bio.controller;


import com.example.bio.common.base.BaseController;
import com.example.bio.service.BioCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
@RestController
@RequestMapping("/bio/bio-comment")
public class BioCommentController extends BaseController {

    @Autowired
    private BioCommentService commentService;

}
