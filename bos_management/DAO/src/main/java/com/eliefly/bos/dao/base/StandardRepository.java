package com.eliefly.bos.dao.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eliefly.bos.domain.base.Standard;

/**
 * ClassName:StandardRepository <br/>
 * Function: <br/>
 * Date: Nov 27, 2017 8:17:31 PM <br/>
 */
public interface StandardRepository extends JpaRepository<Standard, Long> {
    
    // 增加收件标准

    // 分页查询 收件标准
    public Page<Standard> findAll(Pageable pageable);

    // 查询所有收件标准
    public List<Standard> findAll();

    public Standard findOne(Long id);

    public void delete(Long id);

    public Standard findByName(String name);

    // public Standard findByNameLike(String name);

    public List<Standard> findByNameLike(String name);

    public Standard findByOperatorNull();

    public Standard findByNameAndOperator(String name, String operator);

    @Query("from Standard where operator=? and name=?")
    public Standard findByNameAndOperatorJPQL(String operator, String name);

    @Query(value = "select * from T_STANDARD t where t.C_NAME=? and t.C_OPERATOR=?",
            nativeQuery = true)
    public Standard findByNameAndOperatorSQL(String name, String operator);

}
