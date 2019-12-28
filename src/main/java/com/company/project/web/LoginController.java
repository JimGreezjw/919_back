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
@RequestMapping("/")
public class LoginController {
  @Resource
  private UserService userService;

  @PostMapping("/login")
  public Result login(@RequestBody String jsonstr, HttpSession session) {
    JSONObject json = JSONObject.parseObject(jsonstr);
    JSONObject res_json = new JSONObject();
    if (json.containsKey("username") && json.containsKey("password")) {
      User user = userService.findBy("nickName", json.getString("username"));
      if (user != null && user.getPassword().equals(json.getString("password"))) {
        res_json.put("msg", "登陆成功！");
        String token = String.valueOf(System.currentTimeMillis());
        res_json.put("token", token);
        res_json.put("userId", user.getId());
        session.setAttribute(WebSecurityConfig.SESSION_KEY, user.getUsername());
        session.setAttribute("token",token);
      } else {
        res_json.put("msg", "登陆失败！");
        return ResultGenerator.genSuccessResult(res_json);
      }
    } else {
      res_json.put("msg", "登陆失败！");
      return ResultGenerator.genSuccessResult(res_json);
    }
    return ResultGenerator.genSuccessResult(res_json);
  }

  @CrossOrigin(origins = "http://localhost:9528", maxAge = 3600,allowCredentials = "true")
  @PostMapping("/logout")
  public Result logout(HttpSession session) {
    // 移除session
    session.removeAttribute(WebSecurityConfig.SESSION_KEY);
    session.removeAttribute("token");
    String msg="成功登出";
    return ResultGenerator.genSuccessResult(msg);
  }

}

