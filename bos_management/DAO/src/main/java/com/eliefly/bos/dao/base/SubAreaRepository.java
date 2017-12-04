package com.eliefly.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.eliefly.bos.domain.base.SubArea;

/**
 * ClassName:SubAreaRepository <br/>
 * Function: <br/>
 * Date: 2017年12月2日 下午3:43:34 <br/>
 */

public interface SubAreaRepository extends JpaRepository<SubArea, Long> {

    // 查询未关联定区的分区
    @Query(value = "select * from T_SUB_AREA where C_FIXEDAREA_ID is null",
            nativeQuery = true)

    List<SubArea> findNoassociationSubArea();

    // 查询已关联指定定区的分区
    @Query(value = "select * from T_SUB_AREA where C_FIXEDAREA_ID = ?",
            nativeQuery = true)
    List<SubArea> findAssociationSubArea(Long id);

    // 先把指定id的区域的所有分区解绑
    @Modifying
    @Query(value = "update T_SUB_AREA set C_FIXEDAREA_ID = null where C_FIXEDAREA_ID = ?1",
            nativeQuery = true)
    void unAssignAllSubAreasByFixedArea(Long fixedAreaId);

    // 把右边窗口所有的客户都管关联到指定的区域中
    @Modifying
    @Query(value = "update T_SUB_AREA set C_FIXEDAREA_ID = ?1 where C_ID = ?2",
            nativeQuery = true)
    void assignSubArea2FixedArea(Long fixedAreaId, Long subAreaId);

}
