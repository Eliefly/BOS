package com.eliefly.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eliefly.bos.domain.base.FixedArea;

/**
 * ClassName:fixAreaService <br/>
 * Function: <br/>
 * Date: 2017年12月2日 下午8:16:19 <br/>
 */
public interface FixAreaService {

    void save(FixedArea fixedArea);

    Page<FixedArea> pageQuery(Pageable pageable);

}
