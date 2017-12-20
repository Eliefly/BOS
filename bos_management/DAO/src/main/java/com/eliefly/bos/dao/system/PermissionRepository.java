package com.eliefly.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eliefly.bos.domain.system.Permission;

/**
 * ClassName:PermissionRepository <br/>
 * Function: <br/>
 * Date: 2017年12月14日 下午3:56:54 <br/>
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query("select p from Permission p inner join p.roles r inner join r.users u on u.id = ? ")
    List<Permission> findByUid(Long id);

    @Query("select p from Permission p inner join p.roles r on r.id = ?")
    List<Permission> findByRoleId(Long id);

}
