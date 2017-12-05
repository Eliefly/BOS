package com.eliefly.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eliefly.bos.domain.base.TakeTime;

/**
 * ClassName:TakeTimeRepository <br/>
 * Function: <br/>
 * Date: 2017年12月5日 下午12:13:30 <br/>
 */
public interface TakeTimeRepository extends JpaRepository<TakeTime, Long> {

}
