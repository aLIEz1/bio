package com.example.bio.biography.application.executor.command;

import com.alibaba.cola.dto.Response;
import com.example.bio.biography.application.command.ApproveCommentCmd;
import com.example.bio.biography.domain.gateway.BioCommentGateway;
import org.springframework.stereotype.Component;

@Component
public class ApproveCommentCmdExe {

    private final BioCommentGateway commentGateway;

    public ApproveCommentCmdExe(BioCommentGateway commentGateway) {
        this.commentGateway = commentGateway;
    }

    public Response executeSingle(String id) {
        commentGateway.approve(id);
        return Response.buildSuccess();
    }

    public Response executeBatch(ApproveCommentCmd cmd) {
        commentGateway.approveBatch(cmd.getCommentIds());
        return Response.buildSuccess();
    }
}
