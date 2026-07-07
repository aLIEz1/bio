package com.example.bio.biography.application.executor.command;

import com.alibaba.cola.dto.Response;
import com.example.bio.biography.application.command.DeleteCommentCmd;
import com.example.bio.biography.domain.gateway.BioCommentGateway;
import com.example.bio.biography.domain.gateway.BiographyGateway;
import com.example.bio.biography.domain.model.BioComment;
import com.example.bio.exception.Asserts;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteCommentCmdExe {

    private final BioCommentGateway commentGateway;
    private final BiographyGateway biographyGateway;

    public DeleteCommentCmdExe(BioCommentGateway commentGateway,
                               BiographyGateway biographyGateway) {
        this.commentGateway = commentGateway;
        this.biographyGateway = biographyGateway;
    }

    @Transactional(rollbackFor = Exception.class)
    public Response execute(DeleteCommentCmd cmd) {
        BioComment comment = commentGateway.findById(cmd.getCommentId())
                .orElseThrow(() -> Asserts.build("评论不存在"));

        boolean isCommentOwner = comment.getUserId().equals(cmd.getCurrentUserId());
        boolean isBioOwner = biographyGateway.findById(comment.getBioId())
                .map(b -> b.getOwnerId().equals(cmd.getCurrentUserId()))
                .orElse(false);

        if (isCommentOwner) {
            commentGateway.deleteByOwner(cmd.getCommentId(), cmd.getCurrentUserId());
        } else if (isBioOwner) {
            commentGateway.deleteByBioOwner(cmd.getCommentId(), cmd.getCurrentUserId());
        } else if (cmd.isAdmin()) {
            commentGateway.softDelete(cmd.getCommentId());
        } else {
            Asserts.fail("没有权限删除该评论");
        }

        return Response.buildSuccess();
    }
}
