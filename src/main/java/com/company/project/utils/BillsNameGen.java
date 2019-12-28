package com.company.project.utils;

import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;


@Service
public class BillsNameGen {
  public static String genBillsName(Integer typesid,int count) {
    String preff = "919";
    String midff=SdUtils.getYear()+"-S";
    String count_str = String.format("%03d", count+1);
    switch (typesid) {
      case 1:
        preff = "P-919";//偏离单
        break;
      case 2:
        preff = "G-919";//更改单
        break;
      case 3:
        preff = "WXCZ-919";//外协传真
        break;
      case 4:
        preff = "JJD-919";//交接单
        break;
      case 5:
        preff = "919";//会议纪要
        midff="M"+SdUtils.getYear().substring(2);
        break;
      default:
        break;
    }
    return new String(new StringBuilder().append(preff).append("-").append(midff).append(count_str));
  }
}
