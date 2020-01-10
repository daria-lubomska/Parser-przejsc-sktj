package com.sktj.service;

import com.opencsv.exceptions.CsvValidationException;
import com.sktj.entity.ClimbingAchievements;
import com.sktj.entity.User;
import com.sktj.repository.ClimbingRepository;
import com.sktj.repository.UserRepository;
import com.sktj.util.attributes.ClimbingAchievAttributes;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ClimbingAchievementParser {

  private final UserRepository userRepository;
  private final UserService userService;
  private final CountryService countryService;
  private final ClimbingRepository climbingRepository;
  private final CSVFileReader reader;

  @Autowired
  public ClimbingAchievementParser(UserRepository userRepository,
      UserService userService, CountryService countryService,
      ClimbingRepository climbingRepository, CSVFileReader reader) {
    this.userRepository = userRepository;
    this.userService = userService;
    this.countryService = countryService;
    this.climbingRepository = climbingRepository;
    this.reader = reader;
  }

  @Value("classpath:climbing.csv")
  private Resource climbingResource;
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @Transactional
  public void saveDataToDB() {

    try {
      List<String[]> caveRecords = reader.readFile(climbingResource);
      for (String[] line : caveRecords) {
        ClimbingAchievements climbing = new ClimbingAchievements();
        LocalDateTime notificationTimestamp = LocalDateTime
            .parse(line[ClimbingAchievAttributes.NOTIFICATION_TIME.ordinal()].trim(), formatter);
        climbing.setNotificationTimestamp(notificationTimestamp);
        climbing.setDate(LocalDate.parse(line[ClimbingAchievAttributes.DATE.ordinal()].trim()));
        String duration = line[ClimbingAchievAttributes.DURATION.ordinal()].trim();
        if (!duration.isEmpty()) {
          climbing
              .setDuration(
                  Duration.parse(line[ClimbingAchievAttributes.DURATION.ordinal()].trim()));
        } else {
          climbing.setDuration(null);
        }
        climbing.setRouteName(line[ClimbingAchievAttributes.ROUTE_NAME.ordinal()].trim());
        climbing.setDifficultyGrade(line[ClimbingAchievAttributes.DIFFICULTY.ordinal()].trim());
        climbing.setWall(line[ClimbingAchievAttributes.WALL.ordinal()].trim());
        String country = line[ClimbingAchievAttributes.COUNTRY.ordinal()].trim();
        climbing.setCountry(countryService.findCountryByName(country));
        climbing.setRegion(line[ClimbingAchievAttributes.REGION.ordinal()].trim());
        List<User> userBatchList = (List<User>) userRepository.findAll();
        String authors = line[ClimbingAchievAttributes.AUTHORS.ordinal()];
        for (User user : userBatchList) {
          if (authors.toUpperCase().contains(user.getSurname().toUpperCase())) {
            climbing.getAuthors().add(user);
          }
        }
        climbing.setAnotherAuthors(line[ClimbingAchievAttributes.ANOTHER_AUTHORS.ordinal()].trim());
        climbing.setComment(line[ClimbingAchievAttributes.COMMENT.ordinal()].trim());
        String email = line[ClimbingAchievAttributes.NOTIFICATION_AUTHOR.ordinal()].trim();
        climbing.setNotificationAuthor(userService.findUserByEmail(email));
        log.info("climbing achievement notified at {} parsed", notificationTimestamp.toString());
        climbingRepository.save(climbing);
      }
    } catch (IOException | CsvValidationException e) {
      log.error(e.getMessage());
    }
  }
}
