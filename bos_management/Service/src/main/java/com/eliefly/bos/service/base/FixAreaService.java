package com.eliefly.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eliefly.bos.domain.base.FixedArea;
import com.eliefly.bos.domain.base.SubArea;

/**
 * ClassName:fixAreaService <br/>
 * Function: <br/>
 * Date: 2017年12月2日 下午8:16:19 <br/>
 */
public interface FixAreaService {

    void save(FixedArea fixedArea);

    Page<FixedArea> pageQuery(Pageable pageable);

    void associationCourierToFixedArea(Long id, Long courierId,
            Long takeTimeId);

    List<SubArea> findNoassociationSubArea();

    List<SubArea> findAssociationSubArea(Long id);

    void assignSubAreas2FixedArea(Long fixedAreaId, List<Long> subAreaIds);

}
