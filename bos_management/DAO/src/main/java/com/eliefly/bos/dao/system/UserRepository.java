package com.eliefly.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eliefly.bos.domain.system.User;

/**
 * ClassName:UserRepository <br/>
 * Function: <br/>
 * Date: 2017年12月12日 下午9:43:15 <br/>
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
