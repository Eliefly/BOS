package com.eliefly.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eliefly.bos.domain.base.Standard;

/**
 * ClassName:StandardService <br/>
 * Function: <br/>
 * Date: 2017年11月30日 下午4:22:18 <br/>
 */
public interface StandardService {

    void save(Standard standard);

    Page<Standard> pageQuery(Pageable pageable);

    List<Standard> findAll();

}
