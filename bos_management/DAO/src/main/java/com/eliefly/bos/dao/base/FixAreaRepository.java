package com.eliefly.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.eliefly.bos.domain.base.FixedArea;

/**
 * ClassName:FixAreaRepository <br/>
 * Function: <br/>
 * Date: Nov 27, 2017 9:06:12 PM <br/>
 */

public interface FixAreaRepository extends JpaRepository<FixedArea, Long> {

    // 定区关联快递员
    @Modifying
    @Query(value = "insert into T_FIXEDAREA_COURIER values(?1, ?2)",
            nativeQuery = true)
    void associationCourierToFixedArea(Long id, Long courierId);

    @Query(value = "select * from T_FIXEDAREA_COURIER where C_FIXED_AREA_ID = ? and C_COURIER_ID = ?",
            nativeQuery = true)
    Object findCourierToFixedAreaAssociation(Long id, Long courierId);

    // // 查询未关联定区的分区
    // @Query(value = "select * from T_SUB_AREA where C_FIXEDAREA_ID is null",
    // nativeQuery = true)
    //
    // List<SubArea> findNoassociationSubArea();
    //
    // // 查询已关联指定定区的分区
    // @Query(value = "select * from T_SUB_AREA where C_FIXEDAREA_ID = ?",
    // nativeQuery = true)
    // List<SubArea> findAssociationSubArea(Long id);

}
