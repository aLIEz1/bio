package com.example.bio.user.application.command;

import com.alibaba.cola.dto.Command;
import com.example.bio.dto.UpdateUserInfoDto;

public class UpdateUserInfoCmd extends Command {
    private UpdateUserInfoDto dto;

    public UpdateUserInfoDto getDto() { return dto; }
    public void setDto(UpdateUserInfoDto dto) { this.dto = dto; }
}
