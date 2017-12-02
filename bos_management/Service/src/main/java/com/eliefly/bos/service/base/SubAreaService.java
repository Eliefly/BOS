package com.eliefly.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eliefly.bos.domain.base.SubArea;

/**
 * ClassName:SubAreaService <br/>
 * Function: <br/>
 * Date: 2017年12月2日 下午3:39:52 <br/>
 */
public interface SubAreaService {

    void save(SubArea subArea);

    Page<SubArea> pageQuery(Pageable pageable);

}
