package com.example.bio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhangfuqi
 * @date 2020/11/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetToken implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;

    private String token;

}
