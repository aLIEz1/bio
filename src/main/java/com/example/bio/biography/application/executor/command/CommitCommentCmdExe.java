package com.example.bio.biography.application.executor.command;

import com.alibaba.cola.dto.Response;
import com.example.bio.biography.application.command.CommitCommentCmd;
import com.example.bio.biography.domain.gateway.BioCommentGateway;
import com.example.bio.biography.domain.gateway.BiographyGateway;
import com.example.bio.biography.domain.gateway.UserQueryGateway;
import com.example.bio.biography.domain.model.BioComment;
import com.example.bio.exception.Asserts;
import com.example.bio.util.SnowFlakeUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CommitCommentCmdExe {

    private final BioCommentGateway commentGateway;
    private final BiographyGateway biographyGateway;
    private final UserQueryGateway userQueryGateway;

    public CommitCommentCmdExe(BioCommentGateway commentGateway,
                               BiographyGateway biographyGateway,
                               UserQueryGateway userQueryGateway) {
        this.commentGateway = commentGateway;
        this.biographyGateway = biographyGateway;
        this.userQueryGateway = userQueryGateway;
    }

    @Transactional(rollbackFor = Exception.class)
    public Response execute(CommitCommentCmd cmd) {
        biographyGateway.findById(cmd.getBioId())
                .filter(b -> b.isCommentEnabled() && b.getDelFlag() == 0)
                .orElseThrow(() -> Asserts.build("传记不存在或不允许评论"));

        BioComment comment = new BioComment();
        comment.setId(SnowFlakeUtil.nextId().toString());
        comment.setBioId(cmd.getBioId());
        comment.setUserId(cmd.getCurrentUserId());
        comment.setCommentBody(cmd.getCommentBody());
        comment.setCommentStatus(0);
        comment.setParentId(cmd.getParentId());

        commentGateway.save(comment);
        biographyGateway.incrementCommentNum(cmd.getBioId());
        userQueryGateway.incrementCommentNum(cmd.getCurrentUserId());

        return Response.buildSuccess();
    }
}
