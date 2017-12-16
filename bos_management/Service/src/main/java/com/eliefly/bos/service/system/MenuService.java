package com.eliefly.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eliefly.bos.domain.system.Menu;
import com.eliefly.bos.domain.system.User;

/**
 * ClassName:MenuService <br/>
 * Function: <br/>
 * Date: 2017年12月14日 上午11:28:53 <br/>
 */
public interface MenuService {

    List<Menu> findByParentMenuIsNull();

    void save(Menu model);

    Page<Menu> pageQuery(Pageable pageable);

    List<Menu> findByUser(User user);

}
