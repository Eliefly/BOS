package com.eliefly.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eliefly.bos.domain.system.Role;

/**
 * ClassName:RoleService <br/>
 * Function: <br/>
 * Date: 2017年12月15日 下午3:19:47 <br/>
 */
public interface RoleService {

    Page<Role> pageQuery(Pageable pageable);

    void save(Role role, String menuIds, List<Long> permissionIds);

    List<Role> findAll();

    Role findById(Long id);

}
