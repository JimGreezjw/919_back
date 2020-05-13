package com.company.project.web;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


@RequestMapping("/")
@RestController
public class FileUploadController {

  @Value("${spring.fileUploadPath}")
  private String fileUploadPath;

  @PostMapping("/upload")
  public Result uploadApk(@RequestParam("file") MultipartFile file, HttpServletRequest req) throws IOException {

    if (file.isEmpty()) {
      return ResultGenerator.genFailResult("failed");
    }
    // 获取原文件名
    String fileName = file.getOriginalFilename();
    // 创建文件实例
    File filePath = new File(fileUploadPath, fileName);
    String filePathStr = filePath.toString();

    // 如果文件目录不存在，创建目录
    if (!filePath.getParentFile().exists()) {
      filePath.getParentFile().mkdirs();
      System.out.println("创建目录" + filePath);
    }
    // 写入文件
    file.transferTo(filePath);
    return ResultGenerator.genSuccessResult(filePathStr);
  }

  @RequestMapping("/download")
  public Result download(@RequestParam("file") String fileName,
                       HttpServletRequest request,HttpServletResponse resp) throws IOException {
    File file = new File(fileName);
    if (file.exists()) {
      FileInputStream fis = null;
      try {
        fis = new FileInputStream(fileName);
        resp.setContentType("application/vnd.ms-excel;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Disposition",
          "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
        byte[] b = new byte[100];
        int len;
        while ((len = fis.read(b)) > 0) {
          resp.getOutputStream().write(b, 0, len);
        }
      } catch (Exception e) {
        return ResultGenerator.genFailResult("下载失败！");
      } finally {
        resp.getOutputStream().flush();
        resp.getOutputStream().close();
        fis.close();
      }
    }else{
      return null;
    }

    return ResultGenerator.genSuccessResult("文件下载成功！");
  }
}
