package com.eliefly.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eliefly.bos.dao.base.AreaRepository;
import com.eliefly.bos.domain.base.Area;
import com.eliefly.bos.service.base.AreaService;

/**
 * ClassName:AreaServiceImpl <br/>
 * Function: <br/>
 * Date: 2017年12月2日 上午12:37:52 <br/>
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;

    /*
     * 批量保存区域数据
     */
    @Override
    public void save(List<Area> list) {
        areaRepository.save(list);
    }

    /*
     * 分页查询区域
     */
    @Override
    public Page<Area> pageQuery(Pageable pageable) {
        return areaRepository.findAll(pageable);
    }

}
