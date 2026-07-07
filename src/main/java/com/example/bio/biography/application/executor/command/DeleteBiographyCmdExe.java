package com.example.bio.biography.application.executor.command;

import com.alibaba.cola.dto.Response;
import com.example.bio.biography.application.command.DeleteBiographyCmd;
import com.example.bio.biography.domain.gateway.BiographyGateway;
import com.example.bio.exception.Asserts;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteBiographyCmdExe {

    private final BiographyGateway biographyGateway;

    public DeleteBiographyCmdExe(BiographyGateway biographyGateway) {
        this.biographyGateway = biographyGateway;
    }

    @Transactional(rollbackFor = Exception.class)
    public Response execute(DeleteBiographyCmd cmd) {
        biographyGateway.findById(cmd.getBioId())
                .filter(b -> b.getOwnerId().equals(cmd.getCurrentUserId()))
                .orElseThrow(() -> Asserts.build("传记不存在或没有权限删除"));
        biographyGateway.softDelete(cmd.getBioId());
        return Response.buildSuccess();
    }
}
