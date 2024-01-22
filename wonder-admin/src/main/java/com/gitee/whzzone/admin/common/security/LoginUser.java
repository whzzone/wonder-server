package com.gitee.whzzone.admin.common.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gitee.whzzone.admin.system.entity.User;
import com.gitee.whzzone.admin.system.pojo.dto.DeptDTO;
import com.gitee.whzzone.admin.system.pojo.dto.RoleDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author Create by whz at 2023/7/7
 */
@Data
public class LoginUser extends User implements UserDetails {

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("当前请求选择的角色ID")
    private Integer currentRoleId;

    @ApiModelProperty("当前请求选择的部门ID")
    private Integer currentDeptId;

    @ApiModelProperty("用户的所有角色ID")
    private List<Integer> roleIds;

    @ApiModelProperty("用户的所有部门ID")
    private List<Integer> deptIds;

    @ApiModelProperty("部门List")
    private List<DeptDTO> depts;

    @ApiModelProperty("角色List")
    private List<RoleDTO> roles;

    @ApiModelProperty("权限")
    private List<String> permissions;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.getEnabled();
    }
}
