package com.eliefly.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eliefly.bos.dao.system.MenuRepository;
import com.eliefly.bos.domain.system.Menu;
import com.eliefly.bos.domain.system.User;
import com.eliefly.bos.service.system.MenuService;

/**
 * ClassName:MenuServiceImpl <br/>
 * Function: <br/>
 * Date: 2017年12月14日 上午11:28:33 <br/>
 */
@Transactional
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public List<Menu> findByParentMenuIsNull() {

        return menuRepository.findByParentMenuIsNull();
    }

    @Override
    public void save(Menu model) {

        menuRepository.save(model);
    }

    @Override
    public Page<Menu> pageQuery(Pageable pageable) {

        return menuRepository.findAll(pageable);
    }

    @Override
    public List<Menu> findByUser(User user) {

        if ("admin".equals(user.getUsername())) {
            return menuRepository.findAll();
        }

        return menuRepository.findByUser(user.getId());
    }

}
