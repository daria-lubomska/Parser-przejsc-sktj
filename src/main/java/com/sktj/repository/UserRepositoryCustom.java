package com.sktj.repository;

import com.sktj.entity.User;

public interface UserRepositoryCustom {
  User findUserByEmail(String email);
}
