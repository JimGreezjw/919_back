package com.company.project.service;

import com.company.project.model.Bills;
import com.company.project.core.Service;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/12/16.
 */
public interface BillsService extends Service<Bills> {
  public List<Bills> getBillsByUser(Integer userId);
  public List<Bills> getBillsByTypes(Integer typeId, String year);
}
