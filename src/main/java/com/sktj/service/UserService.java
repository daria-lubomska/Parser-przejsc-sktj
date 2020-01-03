package com.sktj.service;

import com.sktj.dao.UserDao;
import com.sktj.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private UserDao dao;

  @Autowired
  public UserService(UserDao dao) {
    this.dao = dao;
  }

  public User findUserByEmail(String email) {
    return dao.findUserByEmail(email);
  }
}
