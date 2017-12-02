package com.eliefly.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eliefly.bos.dao.base.SubAreaRepository;
import com.eliefly.bos.domain.base.SubArea;
import com.eliefly.bos.service.base.SubAreaService;

/**
 * ClassName:SubAreaServiceImpl <br/>
 * Function: <br/>
 * Date: 2017年12月2日 下午3:39:37 <br/>
 */
@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {

    @Autowired
    private SubAreaRepository subAreaRepository;

    /*
     * 保存分区
     */
    @Override
    public void save(SubArea subArea) {
        subAreaRepository.save(subArea);
    }

    /*
     * 分区分页查询
     */
    @Override
    public Page<SubArea> pageQuery(Pageable pageable) {
        return subAreaRepository.findAll(pageable);
    }

}
