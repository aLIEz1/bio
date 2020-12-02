package com.example.bio.controller;


import cn.hutool.core.util.StrUtil;
import com.example.bio.common.api.BaseController;
import com.example.bio.common.api.Result;
import com.example.bio.dto.AddRoleDto;
import com.example.bio.model.ERole;
import com.example.bio.model.Role;
import com.example.bio.model.User;
import com.example.bio.service.RoleService;
import com.example.bio.service.UserCacheService;
import com.example.bio.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangfuqi
 * @since 2020-10-26
 */
@PreAuthorize("hasRole('ADMIN')")
@Api(value = "角色模块", tags = "角色模块")
@RestController
@RequestMapping("/api/role")
public class RoleController extends BaseController {

    private RoleService roleService;

    private UserService userService;

    private UserCacheService userCacheService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserCacheService(UserCacheService userCacheService) {
        this.userCacheService = userCacheService;
    }

    /**
     * 添加后更新缓存
     *
     * @param addRoleDto 添加role的是数据传输对象
     * @return 成功或者失败信息
     */
    @ApiOperation(value = "添加角色")
    @PostMapping("/addRole")
    public Result<?> addRolesForUser(@RequestBody @Valid AddRoleDto addRoleDto) {
        Set<String> strRoles = addRoleDto.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleService.getByRoleName(ERole.ROLE_ADMIN);
                    roles.add(adminRole);
                    break;
                case "company":
                    Role companyRole = roleService.getByRoleName(ERole.ROLE_COMPANY);
                    roles.add(companyRole);
                    break;
                default:
                    Role userRole = roleService.getByRoleName(ERole.ROLE_USER);
                    roles.add(userRole);
            }
        });
        User user = userService.getById(addRoleDto.getUserId());
        if (user != null) {
            roleService.addRole(addRoleDto.getUserId(), roles);
            userCacheService.deleteUserCache(user.getUsername());
            return ok("添加成功");
        } else {
            return fail("未找到用户");
        }
    }

    @ApiOperation(value = "删除用户角色")
    @DeleteMapping("/deleteRole")
    public Result<?> deleteRolesForUser(@RequestBody @Valid AddRoleDto addRoleDto) {
        //TODO 删除用户角色实现
        return null;
    }

    @GetMapping("/getUserRolesById/{id}")
    public Result<?> getUserRolesById(@PathVariable("id") String id) {
        if (StrUtil.isNotBlank(id)) {
            return ok(roleService.getRoleByUserId(id));
        } else {
            return fail("请输入正确的id");
        }
    }

}
