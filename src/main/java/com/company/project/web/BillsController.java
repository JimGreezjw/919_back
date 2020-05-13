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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by CodeGenerator on 2019/12/31.
 */
@RestController
@RequestMapping("/bills")
public class BillsController {
  @Resource
  private BillsService billsService;

  @Resource
  private UserService userService;

  @PostMapping("/add")
  public Result add(@RequestBody String jsonstr, HttpSession session) {
    Bills bills=new Bills();
    JSONObject json = JSONObject.parseObject(jsonstr);
    String typesName="";

    if (json.containsKey("typeId")) {
      int typeId=json.getInteger("typeId");
      List<Bills> billsList=billsService.getBillsByTypes(typeId, SdUtils.getYear());
      typesName= BillsNameGen.genBillsName(typeId,billsList.size());
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
    if(json.containsKey("content")){
      bills.setContent(json.getString("content"));
    }
    if(json.containsKey("filepath")){
      bills.setBak(json.getString("filepath"));
    }
    if(json.containsKey("reason")){
      bills.setReason(json.getString("reason"));
    }
    if(json.containsKey("status")){
      bills.setBillStatus(json.getInteger("status"));
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
  public Result update(@RequestBody String jsonstr, HttpSession session) {
    JSONObject json = JSONObject.parseObject(jsonstr);
    if(json.containsKey("billsId")){
      Bills bills=billsService.findById(json.getInteger("billsId"));
      if(json.containsKey("billStatus")){
        bills.setBillStatus(json.getInteger("billStatus"));
      }
      if(json.containsKey("content")){
        bills.setContent(json.getString("content"));
      }
      if(json.containsKey("filepath")){
        bills.setBak(json.getString("filepath"));
      }
      if(json.containsKey("reason")){
        bills.setReason(json.getString("reason"));
      }
      billsService.update(bills);
      return ResultGenerator.genSuccessResult(bills);
    }
    return ResultGenerator.genFailResult("参数输入不正确");

  }

  @PostMapping("/detail")
  public Result detail(@RequestParam Integer id) {
    Bills bills = billsService.findById(id);
    return ResultGenerator.genSuccessResult(bills);
  }

  @PostMapping("/list")
  public Result list(@RequestBody String jsonstr, HttpSession session) {
    JSONObject json = JSONObject.parseObject(jsonstr);
    JSONObject res_json = new JSONObject();
    int page=0;
    int size=0;
    String type="";
    String year="";
    if(json.containsKey("page")){
      page=json.getInteger("page");
    }
    if(json.containsKey("size")){
      size=json.getInteger("size");
    }
    PageHelper.startPage(page, size);

    if(json.containsKey("type")){
      type=json.getString("type");
    }
    if(json.containsKey("year")){
      year=json.getString("year");
    }
    List<Bills> billsList;
    List<BillsVO> billsVOList = new ArrayList<>();
    if (type != null && type != "") {
      billsList = billsService.getBillsByTypes(Integer.valueOf(type), year);
    } else {
      billsList = billsService.findAll();
    }
    for (Bills bill : billsList) {
      try {
        BillsVO billsVO = new BillsVO();
        billsVO.setBills(bill);
        billsVO.setUser(userService.findById(bill.getUserId()));
        billsVOList.add(billsVO);
      } catch (Exception e) {
        System.out.println(e);
      }
    }
    PageInfo pageInfo = new PageInfo(billsVOList);
    return ResultGenerator.genSuccessResult(pageInfo);
  }

}
