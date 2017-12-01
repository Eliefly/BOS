package com.eliefly.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eliefly.bos.domain.base.Area;

/**  
 * ClassName:AreaService <br/>  
 * Function:  <br/>  
 * Date:     2017年12月2日 上午12:38:24 <br/>       
 */
public interface AreaService {

    void save(List<Area> list);

    Page<Area> pageQuery(Pageable pageable);

}
  
