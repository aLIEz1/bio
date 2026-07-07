package com.example.bio.biography.application.executor.query;

import com.alibaba.cola.dto.MultiResponse;
import com.example.bio.biography.application.query.CommentPageQuery;
import com.example.bio.biography.domain.gateway.BioCommentGateway;
import com.example.bio.biography.domain.model.BioComment;
import com.example.bio.exception.Asserts;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentPageQryExe {

    private final BioCommentGateway commentGateway;

    public CommentPageQryExe(BioCommentGateway commentGateway) {
        this.commentGateway = commentGateway;
    }

    public MultiResponse<BioComment> execute(CommentPageQuery query) {
        if (query.getBioId() == null || query.getBioId().isBlank()) {
            Asserts.fail("bioId不能为空");
        }
        List<BioComment> comments = commentGateway.findApprovedByBioId(query.getBioId());
        return MultiResponse.of(comments);
    }

    public MultiResponse<BioComment> executePending(long current, long size) {
        List<BioComment> comments = commentGateway.findPending(current, size);
        return MultiResponse.of(comments);
    }
}
