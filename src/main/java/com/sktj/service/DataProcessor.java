package com.sktj.service;

import com.sktj.entity.Cave;
import com.sktj.entity.Country;
import com.sktj.entity.User;
import com.sktj.repository.CaveRepository;
import com.sktj.repository.CountryRepository;
import com.sktj.repository.UserRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Slf4j
@Component("usersLoader")
public class DataProcessor {

  private UserRepository userRepository;
  private CaveRepository caveRepository;
  private CountryRepository countryRepository;

  @Value("classpath:users.csv")
  Resource userResource;

  @Value("classpath:caves.csv")
  Resource caveResource;

  @Value("classpath:countries.csv")
  Resource countryResource;

  @Autowired
  public DataProcessor(UserRepository userRepository, CaveRepository caveRepository,
      CountryRepository countryRepository) {
    this.userRepository = userRepository;
    this.caveRepository = caveRepository;
    this.countryRepository = countryRepository;
  }

  private List<String> readFile(Resource resource) throws IOException {
    InputStream in = resource.getInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    return reader.lines().collect(Collectors.toList());
  }

  @PostConstruct
  public void saveDataToDB() throws IOException {
    List<String> userRecords = readFile(userResource);
    for (String record : userRecords) {
      String[] line = record.split(",");
      User user = new User();
      user.setCardNumber(Integer.parseInt(line[0]));
      user.setRole(line[1]);
      user.setSurname(line[2].trim());
      user.setName(line[3].trim());
      user.setEmail(line[4].trim());
      log.info("user {} was saved to DB", Arrays.toString(line));
      userRepository.save(user);
    }

    List<String> caveRecords = readFile(caveResource);
    for (String record : caveRecords) {
      String[] line = record.split(",");
      Cave cave = new Cave(line[0].trim(), line[1].trim());
      log.info("cave {} was saved to DB", Arrays.toString(line));
      caveRepository.save(cave);
    }

    List<String> countryRecords = readFile(countryResource);
    for (String record : countryRecords) {
      String[] line = record.split(",");
      Country country = new Country(line[0].trim());
      log.info("cave {} was saved to DB", Arrays.toString(line));
      countryRepository.save(country);
    }
  }
}
