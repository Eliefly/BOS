package com.eliefly.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eliefly.bos.dao.system.PermissionRepository;
import com.eliefly.bos.domain.system.Permission;
import com.eliefly.bos.service.system.PermissionService;

/**
 * ClassName:PermissionServiceImpl <br/>
 * Function: <br/>
 * Date: 2017年12月14日 下午3:54:12 <br/>
 */
@Transactional
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Page<Permission> pageQuery(Pageable pageable) {

        return permissionRepository.findAll(pageable);
    }

    @Override
    public void save(Permission model) {

        permissionRepository.save(model);
    }

    @Override
    public List<Permission> findAll() {

        return permissionRepository.findAll();
    }

}
