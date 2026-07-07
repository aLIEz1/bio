package com.example.bio.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 更新用户详细信息 DTO
 *
 * @author zhangfuqi
 */
@ApiModel(value = "UpdateUserInfoDto", description = "更新用户信息请求体")
public class UpdateUserInfoDto {

    @ApiModelProperty(value = "生日（格式：yyyy-MM-dd）")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @ApiModelProperty(value = "所属组织")
    private String organization;

    @ApiModelProperty(value = "爱好")
    private String hobby;

    @ApiModelProperty(value = "性别：0-未设置，1-男，2-女")
    private Integer gender;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "地址")
    private String address;

    public Date getBirthday() { return birthday; }
    public void setBirthday(Date birthday) { this.birthday = birthday; }
    public String getOrganization() { return organization; }
    public void setOrganization(String organization) { this.organization = organization; }
    public String getHobby() { return hobby; }
    public void setHobby(String hobby) { this.hobby = hobby; }
    public Integer getGender() { return gender; }
    public void setGender(Integer gender) { this.gender = gender; }
    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
