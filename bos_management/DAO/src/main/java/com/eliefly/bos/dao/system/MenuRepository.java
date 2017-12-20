package com.eliefly.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eliefly.bos.domain.system.Menu;

/**
 * ClassName:MenuRepository <br/>
 * Function: <br/>
 * Date: 2017年12月14日 上午11:32:12 <br/>
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByParentMenuIsNull();

    @Query("select m from Menu m inner join m.roles r inner join r.users u on u.id = ?")
    List<Menu> findByUser(Long id);

    @Query("select m from Menu m inner join m.roles r on r.id = ?")
    List<Menu> findByRoleId(Long id);

}
