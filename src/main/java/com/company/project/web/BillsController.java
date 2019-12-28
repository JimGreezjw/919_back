package com.company.project.web;

import com.alibaba.fastjson.JSONObject;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.Bills;
import com.company.project.service.BillsService;
import com.company.project.service.UserService;
import com.company.project.utils.BillsNameGen;
import com.company.project.utils.SdUtils;
import com.company.project.vo.BillsVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by CodeGenerator on 2019/12/16.
 */
@RestController
@RequestMapping("/bills")
public class BillsController {
  @Resource
  private BillsService billsService;

  @Resource
  private UserService userService;

  @PostMapping("/add")
  public Result add(@RequestBody String jsonstr) {
    Bills bills=new Bills();
    JSONObject json = JSONObject.parseObject(jsonstr);
    String typesName="";

    if (json.containsKey("typeId")) {
      int typeId=json.getInteger("typeId");
      List<Bills> billsList=billsService.getBillsByTypes(typeId,SdUtils.getYear());
      typesName=BillsNameGen.genBillsName(typeId,billsList.size());
      bills.setTypeId(typeId);
      bills.setName(typesName);
    }else{
      return ResultGenerator.genFailResult("请输入三单类型");
    }
    if (json.containsKey("userId")) {
      bills.setUserId(json.getInteger("userId"));
    }else{
      return ResultGenerator.genFailResult("请输入要号用户");
    }
    bills.setCreateDate(new Date());
    billsService.save(bills);
    return ResultGenerator.genSuccessResult(bills);
  }

  @PostMapping("/delete")
  public Result delete(@RequestParam Integer id) {
    billsService.deleteById(id);
    return ResultGenerator.genSuccessResult();
  }

  @PostMapping("/update")
  public Result update(Bills bills) {
    billsService.update(bills);
    return ResultGenerator.genSuccessResult();
  }

  @PostMapping("/detail")
  public Result detail(@RequestParam Integer id) {
    Bills bills = billsService.findById(id);
    return ResultGenerator.genSuccessResult(bills);
  }

  @GetMapping("/list")
  public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size,@RequestParam() String type,@RequestParam(defaultValue = "0") String year) {
    PageHelper.startPage(page, size);
    List<Bills> billsList;
    List<BillsVO> billsVOList=new ArrayList<>();
    if(type!=null && type!="" && year!="" && year!=null){
      billsList= billsService.getBillsByTypes(Integer.valueOf(type),year);
    }else{
      billsList = billsService.findAll();
    }
    for(Bills bill:billsList){
      try{
        BillsVO billsVO=new BillsVO();
        billsVO.setBills(bill);
        billsVO.setUser(userService.findById(bill.getUserId()));
        billsVOList.add(billsVO);
      }catch (Exception e){
        System.out.println(e);
      }
    }
    PageInfo pageInfo = new PageInfo(billsVOList);
    return ResultGenerator.genSuccessResult(pageInfo);
  }
}
