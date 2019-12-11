package sktj.parser.repository;

import org.springframework.stereotype.Repository;
import sktj.parser.entity.User;

@Repository
public interface UserRepositoryCustom {
  User findUserByEmail(String email);
}
