package com.eliefly.bos.dao.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.eliefly.bos.domain.base.Courier;

/**
 * ClassName:CourierRepository <br/>
 * Function: <br/>
 * Date: 2017年12月1日 上午10:24:14 <br/>
 */
public interface CourierRepository extends JpaRepository<Courier, Long>,
        JpaSpecificationExecutor<Courier> {

    // JpaRepository 已提供
    // public void save(Courier courier);

    // 分页查询快递员
    public Page<Courier> findAll(Pageable pageable);

    // 作废快递员
    @Modifying
    @Query("update Courier set deltag = ? where id = ?")
    public void updateDelTagJPQL(Character state, Long id);
}
