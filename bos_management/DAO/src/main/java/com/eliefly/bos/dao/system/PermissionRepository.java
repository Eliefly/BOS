package com.eliefly.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eliefly.bos.domain.system.Permission;

/**
 * ClassName:PermissionRepository <br/>
 * Function: <br/>
 * Date: 2017年12月14日 下午3:56:54 <br/>
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
