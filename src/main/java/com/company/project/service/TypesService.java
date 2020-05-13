package com.company.project.service;
import com.company.project.model.Types;
import com.company.project.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/12/16.
 */
public interface TypesService extends Service<Types> {
  public List<Types> findByTypeName(String typeName);

}
