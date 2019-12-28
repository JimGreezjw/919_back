package com.company.project.service.impl;

import com.company.project.dao.BillsMapper;
import com.company.project.model.Bills;
import com.company.project.service.BillsService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2019/12/16.
 */
@Service
@Transactional
public class BillsServiceImpl extends AbstractService<Bills> implements BillsService {
    @Resource
    private BillsMapper billsMapper;

  @Override
  public List<Bills> getBillsByUser(Integer userId) {
    List<Bills> billsList=billsMapper.findByUserId(userId);
    return billsList;
  }

  @Override
  public List<Bills> getBillsByTypes(Integer typeId,String year) {
    List<Bills> billsList=billsMapper.findByTypesId(typeId,year);
    return billsList;
  }
}
