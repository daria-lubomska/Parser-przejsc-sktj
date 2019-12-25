package com.sktj.repository;

import com.sktj.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryCustom {
  User findUserByEmail(String email);
}
