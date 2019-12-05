package sktj.parser.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import sktj.parser.entity.User;
import sktj.parser.repository.UserRepository;

@Slf4j
@Component("usersLoader")
public class UserProcessor {

  private UserRepository userRepository;

  @Value("classpath:users.csv")
  Resource usersResource;

  @Autowired
  public UserProcessor(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  private List<String> readFile() throws IOException {
    InputStream in = usersResource.getInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    return reader.lines().collect(Collectors.toList());
  }

  public void saveUsersToDB() throws IOException {
    List<String> userRecords = readFile();
    for (String record : userRecords) {
      String[] line = record.split(",");
      User user = new User();
      user.setSurname(line[0].trim());
      user.setName(line[1].trim());
      user.setEmail(line[2].trim());
      log.info("user {} was saved to DB", Arrays.toString(line));
      userRepository.save(user);
    }
  }


}
