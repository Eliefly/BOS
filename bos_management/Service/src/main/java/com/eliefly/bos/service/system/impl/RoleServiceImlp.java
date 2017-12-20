package com.eliefly.bos.service.system.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eliefly.bos.dao.system.RoleRepository;
import com.eliefly.bos.domain.system.Menu;
import com.eliefly.bos.domain.system.Permission;
import com.eliefly.bos.domain.system.Role;
import com.eliefly.bos.service.system.RoleService;

/**
 * ClassName:RoleServiceImlp <br/>
 * Function: <br/>
 * Date: 2017年12月15日 下午3:20:08 <br/>
 */
@Transactional
@Service
public class RoleServiceImlp implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Page<Role> pageQuery(Pageable pageable) {

        return roleRepository.findAll(pageable);
    }

    @Override
    public void save(Role role, String menuIds, List<Long> permissionIds) {

        // 保存之后role就是持久态 的, 利用持久态对象更新其他信息. 放在最后会报错.
        if (role.getId() != null) {

            Role roleDB = roleRepository.findOne(role.getId());
            roleDB.setDescription(role.getDescription());
            roleDB.setKeyword(role.getKeyword());
            roleDB.setName(role.getName());
            roleDB.setMenus(new HashSet<Menu>());
            roleDB.setPermissions(new HashSet<Permission>());

            role = roleDB;

        } else {

            roleRepository.save(role);
        }

        Set<Menu> menus = role.getMenus();
        Set<Permission> permissions = role.getPermissions();

        if (StringUtils.isNoneEmpty(menuIds)) {

            String[] arr = menuIds.split(",");

            for (String str : arr) {
                System.out.println("menuID:" + arr);

                Menu menu = new Menu();
                menu.setId(Long.parseLong(str)); // 托管态的Menu
                menus.add(menu);
            }

        }

        if (permissionIds != null && permissionIds.size() > 0) {
            for (Long permissionId : permissionIds) {
                System.out.println("permissionId: " + permissionId);

                Permission permission = new Permission();
                permission.setId(permissionId); // 托管态的 Permissiono
                permissions.add(permission);
            }
        }

        System.out.println("aaaa");

    }

    @Override
    public List<Role> findAll() {

        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long id) {

        return roleRepository.findOne(id);
    }
}
