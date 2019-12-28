package com.company.project.service.impl;

import com.company.project.dao.VisitsMapper;
import com.company.project.model.Visits;
import com.company.project.service.VisitsService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/12/16.
 */
@Service
@Transactional
public class VisitsServiceImpl extends AbstractService<Visits> implements VisitsService {
    @Resource
    private VisitsMapper visitsMapper;

}
