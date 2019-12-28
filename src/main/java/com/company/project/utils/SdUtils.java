package com.company.project.utils;

import java.util.Calendar;

public class SdUtils {
  public static String getYear(){
    Calendar date = Calendar.getInstance();
    return String.valueOf(date.get(Calendar.YEAR));
  }
}
