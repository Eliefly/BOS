package com.eliefly.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eliefly.bos.dao.base.FixAreaRepository;
import com.eliefly.bos.domain.base.FixedArea;
import com.eliefly.bos.service.base.FixAreaService;

/**
 * ClassName:fixAreaServiceImpl <br/>
 * Function: <br/>
 * Date: 2017年12月2日 下午8:16:02 <br/>
 */

@Service
@Transactional
public class FixAreaServiceImpl implements FixAreaService {

    @Autowired
    private FixAreaRepository fixAreaRepository;

    /*
     * 定区保存
     */
    @Override
    public void save(FixedArea fixedArea) {
        fixAreaRepository.save(fixedArea);
    }

    /*
     * 定区分页查询
     */
    @Override
    public Page<FixedArea> pageQuery(Pageable pageable) {
        return fixAreaRepository.findAll(pageable);
    }

}
