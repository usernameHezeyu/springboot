package com.example.spring.security.spring.mapper;

import org.springframework.stereotype.Repository;
import com.example.spring.security.spring.pojo.SaleDaily;

@Repository
public interface SaleDailyDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SaleDaily record);

    int insertSelective(SaleDaily record);

    SaleDaily selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SaleDaily record);

    int updateByPrimaryKey(SaleDaily record);
}