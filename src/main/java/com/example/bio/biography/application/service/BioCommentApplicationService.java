package com.example.bio.biography.application.service;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.example.bio.biography.application.command.ApproveCommentCmd;
import com.example.bio.biography.application.command.CommitCommentCmd;
import com.example.bio.biography.application.command.DeleteCommentCmd;
import com.example.bio.biography.application.executor.command.ApproveCommentCmdExe;
import com.example.bio.biography.application.executor.command.CommitCommentCmdExe;
import com.example.bio.biography.application.executor.command.DeleteCommentCmdExe;
import com.example.bio.biography.application.executor.query.CommentPageQryExe;
import com.example.bio.biography.application.query.CommentPageQuery;
import com.example.bio.biography.domain.model.BioComment;
import org.springframework.stereotype.Service;

@Service
public class BioCommentApplicationService {

    private final CommitCommentCmdExe commitExe;
    private final DeleteCommentCmdExe deleteExe;
    private final ApproveCommentCmdExe approveExe;
    private final CommentPageQryExe pageQryExe;

    public BioCommentApplicationService(CommitCommentCmdExe commitExe,
                                        DeleteCommentCmdExe deleteExe,
                                        ApproveCommentCmdExe approveExe,
                                        CommentPageQryExe pageQryExe) {
        this.commitExe = commitExe;
        this.deleteExe = deleteExe;
        this.approveExe = approveExe;
        this.pageQryExe = pageQryExe;
    }

    public MultiResponse<BioComment> getCommentsPage(CommentPageQuery query) {
        return pageQryExe.execute(query);
    }

    public MultiResponse<BioComment> getPendingComments(long current, long size) {
        return pageQryExe.executePending(current, size);
    }

    public Response commitComment(CommitCommentCmd cmd) {
        return commitExe.execute(cmd);
    }

    public Response deleteComment(DeleteCommentCmd cmd) {
        return deleteExe.execute(cmd);
    }

    public Response approveComment(String id) {
        return approveExe.executeSingle(id);
    }

    public Response approveCommentBatch(ApproveCommentCmd cmd) {
        return approveExe.executeBatch(cmd);
    }
}
