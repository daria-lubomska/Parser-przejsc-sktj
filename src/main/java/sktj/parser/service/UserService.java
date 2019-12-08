package sktj.parser.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sktj.parser.entity.User;
import sktj.parser.repository.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void save(User user){
    userRepository.save(user);
  }

  public User findUserByEmail(String email){
     return userRepository.findUserByEmail(email);
  }

  public User findUserByNameAndSurname(String name, String surname){
    return userRepository.findUserByNameAndSurname(name,surname);
  }

  public List<User> findUserForLiveSearch(String someChars){
    return userRepository.findUserForLiveSearch(someChars);
  }
}
