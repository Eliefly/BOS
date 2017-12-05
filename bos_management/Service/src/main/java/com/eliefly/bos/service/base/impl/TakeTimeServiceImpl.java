package com.eliefly.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eliefly.bos.dao.base.TakeTimeRepository;
import com.eliefly.bos.domain.base.TakeTime;
import com.eliefly.bos.service.base.TakeTimeService;

/**
 * ClassName:TakeTimeServiceImpl <br/>
 * Function: <br/>
 * Date: 2017年12月5日 下午12:10:36 <br/>
 */

@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {

    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Override
    public List<TakeTime> findAll() {

        return takeTimeRepository.findAll();
    }

}
