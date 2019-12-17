package com.sktj.repository;

import org.springframework.stereotype.Repository;
import com.sktj.entity.User;

@Repository
public interface UserRepositoryCustom {
  User findUserByEmail(String email);
}
