package com.eliefly.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eliefly.bos.dao.base.StandardRepository;
import com.eliefly.bos.domain.base.Standard;
import com.eliefly.bos.service.base.StandardService;

/**
 * ClassName:StandardServiceImpl <br/>
 * Function: <br/>
 * Date: 2017年11月30日 下午4:23:51 <br/>
 */

@Service
@Transactional
public class StandardServiceImpl implements StandardService {

    @Autowired
    private StandardRepository standardRepository;

    /*
     * 增加收件标准
     */
    @Override
    public void save(Standard standard) {
        standardRepository.save(standard);
    }

    /*
     * 分页查询收件标准
     */
    @Override
    public Page<Standard> pageQuery(Pageable pageable) {
        return standardRepository.findAll(pageable);
    }

    /*
     * 查询所有收件标准
     */
    @Override
    public List<Standard> findAll() {
        return standardRepository.findAll();
    }

}
