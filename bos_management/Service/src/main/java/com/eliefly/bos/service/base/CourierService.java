package com.eliefly.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.eliefly.bos.domain.base.Courier;

/**
 * ClassName:CourierService <br/>
 * Function: <br/>
 * Date: 2017年12月1日 上午10:21:38 <br/>
 */
public interface CourierService {

    void save(Courier courier);

    Page<Courier> findAll(Specification<Courier> spec, Pageable pageable);

    void delete(String ids);

    List<Courier> findValidCourier();

}
