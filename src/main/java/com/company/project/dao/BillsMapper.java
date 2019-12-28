package com.company.project.dao;

import com.company.project.core.Mapper;
import com.company.project.model.Bills;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillsMapper extends Mapper<Bills> {
  public List<Bills> findByUserId(Integer userId);

  public List<Bills> findByTypesId(@Param(value ="typeId")  Integer typesId, @Param(value ="year")  String year);
}
