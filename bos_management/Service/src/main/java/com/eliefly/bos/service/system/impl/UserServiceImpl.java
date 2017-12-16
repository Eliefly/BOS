package com.eliefly.bos.service.system.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eliefly.bos.dao.system.UserRepository;
import com.eliefly.bos.domain.system.Role;
import com.eliefly.bos.domain.system.User;
import com.eliefly.bos.service.system.UserService;

/**
 * ClassName:UserServiceImpl <br/>
 * Function: <br/>
 * Date: 2017年12月15日 下午5:50:08 <br/>
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user, List<Long> roleIds) {

        if (roleIds != null && roleIds.size() > 0) {

            userRepository.save(user);
            Set<Role> roles = user.getRoles();

            for (Long roleId : roleIds) {
                Role role = new Role();
                role.setId(roleId);
                roles.add(role);
            }

        }

    }

    @Override
    public Page<User> pageQuery(Pageable pageable) {

        return userRepository.findAll(pageable);
    }

}
