package com.example.bio.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.bio.common.api.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@TableName("user_info")
@ApiModel(value = "UserInfo对象", description = "")
public class UserInfo extends BaseEntity {

    @ApiModelProperty(value = "关联的用户id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "生日")
    @TableField("birthday")
    private Date birthday;

    @ApiModelProperty(value = "所属组织")
    @TableField("organization")
    private String organization;

    @ApiModelProperty(value = "爱好")
    @TableField("hobby")
    private String hobby;

    @ApiModelProperty(value = "0-未设置，1-男，2-女")
    @TableField("gender")
    private Integer gender;

    @ApiModelProperty(value = "电话")
    @TableField("tel")
    private String tel;

    @ApiModelProperty(value = "地址")
    @TableField("address")
    private String address;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
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
