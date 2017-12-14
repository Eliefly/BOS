package com.eliefly.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eliefly.bos.domain.system.Permission;

/**  
 * ClassName:PermissionService <br/>  
 * Function:  <br/>  
 * Date:     2017年12月14日 下午3:53:45 <br/>       
 */
public interface PermissionService {

    Page<Permission> pageQuery(Pageable pageable);

    void save(Permission model);

}
  
