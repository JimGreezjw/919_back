package com.company.project.dao;

import com.company.project.core.Mapper;
import com.company.project.model.Bills;
import com.company.project.model.Types;

import java.util.List;

public interface TypesMapper extends Mapper<Types> {
  public List<Types> findByTypeName(String typeName);
}
