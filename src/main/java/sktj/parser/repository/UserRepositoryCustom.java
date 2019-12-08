package sktj.parser.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import sktj.parser.entity.User;

@Repository
public interface UserRepositoryCustom {
  User findUserByEmail(String email);
  User findUserByNameAndSurname(String name, String surname);
  List<User> findUserForLiveSearch(String someChars);
}
