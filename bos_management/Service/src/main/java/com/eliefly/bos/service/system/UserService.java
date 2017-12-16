package com.eliefly.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eliefly.bos.domain.system.User;

/**
 * ClassName:UserService <br/>
 * Function: <br/>
 * Date: 2017年12月15日 下午5:49:51 <br/>
 */
public interface UserService {

    void save(User model, List<Long> roleIds);

    Page<User> pageQuery(Pageable pageable);

}
