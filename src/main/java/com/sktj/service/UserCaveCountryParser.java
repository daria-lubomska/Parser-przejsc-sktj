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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserCaveCountryParser {

  private final UserRepository userRepository;
  private final CaveRepository caveService;
  private final CountryRepository countryRepository;

  @Value("classpath:users.csv")
  private Resource userResource;

  @Value("classpath:caves.csv")
  private Resource caveResource;

  @Value("classpath:countries.csv")
  private Resource countryResource;

  @Autowired
  public UserCaveCountryParser(UserRepository userRepository, CaveRepository caveService,
      CountryRepository countryRepository) {
    this.userRepository = userRepository;
    this.caveService = caveService;
    this.countryRepository = countryRepository;
  }

  private List<String> readFile(Resource resource) throws IOException {
    InputStream in = resource.getInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    return reader.lines().collect(Collectors.toList());
  }

  void saveDataToDB() {
    try {
      List<String> userRecords = readFile(userResource);
      List<String> caveRecords = readFile(caveResource);
      List<String> countryRecords = readFile(countryResource);
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

      for (String record : caveRecords) {
        String[] line = record.split(",");
        Cave cave = new Cave(line[0].trim(), line[1].trim());
        log.info("cave {} was saved to DB", Arrays.toString(line));
        caveService.save(cave);
      }

      for (String record : countryRecords) {
        String[] line = record.split(",");
        Country country = new Country(line[0].trim());
        log.info("cave {} was saved to DB", Arrays.toString(line));
        countryRepository.save(country);
      }
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }
}
