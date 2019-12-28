package com.company.project.vo;

import com.company.project.model.Bills;
import com.company.project.model.User;

public class BillsVO {
  public Bills getBills() {
    return bills;
  }

  public void setBills(Bills bills) {
    this.bills = bills;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  private Bills bills;
  private User user;
}
