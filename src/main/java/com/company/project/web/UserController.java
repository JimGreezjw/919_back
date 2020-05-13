package com.company.project.web;

import com.company.project.configurer.WebSecurityConfig;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.Bills;
import com.company.project.model.User;
import com.company.project.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpSession;


import javax.annotation.Resource;
import java.util.List;

/**
 * Created by CodeGenerator on 2019/12/15.
 */
@RestController
@RequestMapping("/user")
public class UserController {
  @Resource
  private UserService userService;

  @RequestMapping("/add")
  public Result add_user(@RequestBody String jsonstr) {
    JSONObject json = JSONObject.parseObject(jsonstr);

    User user = new User();
    if (json.containsKey("userName")) {
      user.setUsername(json.getString("userName"));
    }
    if (json.containsKey("password")) {
      user.setPassword(json.getString("password"));
    }
    if (json.containsKey("nickName")) {
      user.setNickName(json.getString("nickName"));
    }
    if (json.containsKey("sex")) {
      user.setSex(json.getInteger("sex"));
    }
    if (json.containsKey("register_date")) {
      user.setRegisterDate(json.getDate("register_date"));
    }

    userService.save(user);
    return ResultGenerator.genSuccessResult();
  }

  @PostMapping("/delete")
  public Result delete(@RequestParam Integer id) {
    userService.deleteById(id);
    return ResultGenerator.genSuccessResult();
  }

  @PostMapping("/update")
  public Result update(@RequestBody String jsonstr, HttpSession session) {
    JSONObject json = JSONObject.parseObject(jsonstr);
    if(json.containsKey("id") && json.containsKey("oldPass") && json.containsKey("pass")){
      User user=userService.findById(json.getInteger("id"));
      if(user==null){
        return ResultGenerator.genFailResult("未找到用户");
      }
      if(user.getPassword().equals(json.getString("oldPass"))){
        user.setPassword(json.getString("pass"));
        userService.update(user);
        return ResultGenerator.genSuccessResult("密码修改成功!");
      }else{
        return ResultGenerator.genFailResult("输入用户密码错误");
      }
    }else{
      return ResultGenerator.genFailResult("更改用户密码输入不完全");
    }
  }

  @PostMapping("/detail")
  public Result detail(@RequestParam Integer id) {
    User user = userService.findById(id);
    return ResultGenerator.genSuccessResult(user);
  }

  @GetMapping("/list")
  public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
    PageHelper.startPage(page, size);
    List<User> list = userService.findAll();
    PageInfo pageInfo = new PageInfo(list);
    return ResultGenerator.genSuccessResult(pageInfo);
  }

  @GetMapping("/info")
  public Result info(@RequestParam(defaultValue = "0") String token) {
    String name = token.substring(0, token.indexOf('_'));
    JSONObject res_json = new JSONObject();
    res_json.put("name", name);
    return ResultGenerator.genSuccessResult(res_json);
  }
}
