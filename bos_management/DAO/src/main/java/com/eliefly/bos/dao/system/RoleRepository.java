package com.eliefly.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eliefly.bos.domain.system.Role;

/**
 * ClassName:RoleRepository <br/>
 * Function: <br/>
 * Date: 2017年12月15日 下午3:22:47 <br/>
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r from Role r inner join r.users u on u.id = ?")
    List<Role> findByUid(Long id);

}
