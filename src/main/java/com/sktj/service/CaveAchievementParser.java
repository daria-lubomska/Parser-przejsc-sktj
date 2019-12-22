package com.sktj.service;

import com.opencsv.exceptions.CsvValidationException;
import com.sktj.entity.Cave;
import com.sktj.entity.CaveAchievements;
import com.sktj.entity.User;
import com.sktj.repository.CaveAchievementsRepository;
import com.sktj.repository.CaveRepository;
import com.sktj.repository.CountryRepository;
import com.sktj.repository.UserRepository;
import com.sktj.util.attributes.CaveAchievAttributes;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class CaveAchievementParser {

  private final UserRepository userRepository;
  private final CaveAchievementsRepository caveAchievementsRepository;
  private final CountryRepository countryRepository;
  private final CaveRepository caveRepository;
  private final CSVFileReader reader;

  @Autowired
  public CaveAchievementParser(UserRepository userRepository,
      CaveAchievementsRepository caveAchievementsRepository,
      CountryRepository countryRepository, CaveRepository caveRepository,
      CSVFileReader reader) {
    this.userRepository = userRepository;
    this.caveAchievementsRepository = caveAchievementsRepository;
    this.countryRepository = countryRepository;
    this.caveRepository = caveRepository;
    this.reader = reader;
  }

  @Value("classpath:caveAchiev.csv")
  private Resource caveResource;

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @Transactional
  public void saveDataToDB() throws IOException, CsvValidationException {

    List<String[]> caveRecords = reader.readFile(caveResource);
    for (String[] line : caveRecords) {
      LocalDateTime notificationTimestamp = LocalDateTime
          .parse(line[CaveAchievAttributes.NOTIFICATION_TIME.ordinal()].trim(), formatter);
      LocalDateTime entryTimestamp = parse(line[CaveAchievAttributes.ENTRY_TIME.ordinal()].trim());
      LocalDateTime exitTimestamp = parse(line[CaveAchievAttributes.EXIT_TIME.ordinal()].trim());
      CaveAchievements cave = new CaveAchievements();
      cave.setNotificationTimestamp(notificationTimestamp);
      cave.setEntryTimestamp(entryTimestamp);
      cave.setExitTimestamp(compareTime(entryTimestamp, exitTimestamp));
      String caveName = line[CaveAchievAttributes.CAVE_NAME.ordinal()].trim();
      String region = line[CaveAchievAttributes.REGION.ordinal()].trim();

      if (caveRepository.findByNameAndRegion(caveName, region) == null) {
        Cave newCave = new Cave(caveName, region);
        caveRepository.save(newCave);
        cave.setCaveName(newCave);
      } else {
        cave.setCaveName(caveRepository.findByNameAndRegion(caveName, region));
      }

      cave.setReachedParts(line[CaveAchievAttributes.REACHED_PARTS.ordinal()].trim());
      cave.setCaveOvercomeStyle(line[CaveAchievAttributes.CAVE_OVERCOME_STYLE.ordinal()]
          .trim().toUpperCase());
      String country = line[CaveAchievAttributes.COUNTRY.ordinal()].trim();
      List<User> userBatchList = userRepository.findAll();
      String authors = line[CaveAchievAttributes.AUTHORS.ordinal()];

      for (User user : userBatchList) {
        if (authors.toUpperCase().contains(user.getSurname().toUpperCase())) {
          cave.getAuthors().add(user);
        }
      }
      cave.setCountry(countryRepository.findCountryByName(country));
      cave.setAnotherAuthors(line[CaveAchievAttributes.ANOTHER_AUTHORS.ordinal()].trim());
      cave.setComment(line[CaveAchievAttributes.COMMENT.ordinal()].trim());
      String email = line[CaveAchievAttributes.NOTIFICATION_AUTHOR.ordinal()].trim();
      cave.setNotificationAuthor(userRepository.findUserByEmail(email));
      log.info("cave {} achievement on {}", caveName, notificationTimestamp.toString());
      caveAchievementsRepository.save(cave);
    }
  }

  private LocalDateTime parse(String timestamp) {

    String trimmedTimestamp = timestamp.trim();
    Pattern datePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
    if (datePattern.matcher(trimmedTimestamp).matches()) {
      trimmedTimestamp = trimmedTimestamp + " 00:00:00";
    }
    Pattern dateTimePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}"); // TODO check
    if (dateTimePattern.matcher(trimmedTimestamp).matches()) {
      trimmedTimestamp = trimmedTimestamp + ":00";
    }
    return LocalDateTime.parse(trimmedTimestamp, formatter);
  }

  private LocalDateTime compareTime(LocalDateTime enter, LocalDateTime exit) {

    LocalDateTime processedExit = exit;
    if (enter.equals(exit)) {
      processedExit = exit.plusHours(1);
    }
    return processedExit;
  }
}
