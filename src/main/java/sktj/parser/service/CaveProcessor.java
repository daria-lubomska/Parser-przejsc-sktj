package sktj.parser.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sktj.parser.entity.CaveAchievements;
import sktj.parser.entity.Users;
import sktj.parser.enums.CaveOvercomeStyle;
import sktj.parser.repository.CaveAchievementsRepository;
import sktj.parser.repository.CountryRepository;
import sktj.parser.repository.UserRepository;

@Slf4j
@Component("fileLoader")
public class CaveProcessor {

  private UserRepository userRepository;
  private CaveAchievementsRepository caveRepository;
  private CountryRepository countryRepository;

  @Autowired
  public CaveProcessor(UserRepository userRepository, CaveAchievementsRepository caveRepository,
      CountryRepository countryRepository) {
    this.userRepository = userRepository;
    this.caveRepository = caveRepository;
    this.countryRepository = countryRepository;
  }


  @Value("classpath:caves.csv")
  Resource caveResource;

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private List<String[]> readFile() throws IOException, CsvValidationException {
    InputStream in = caveResource.getInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    CSVReader csvReader = new CSVReader(reader);
    List<String[]> list = new ArrayList<>();
    String[] line;
    while ((line = csvReader.readNext()) != null) {
      list.add(line);
    }
    reader.close();
    csvReader.close();
    return list;
  }

  @Transactional
  public void saveDataToDB() throws IOException, CsvValidationException {
    List<String[]> caveRecords = readFile();
    for (String[] line : caveRecords) {
      LocalDateTime notificationTimestamp = LocalDateTime.parse(line[0].trim(), formatter);
      LocalDateTime entryTimestamp = parse(line[1].trim());
      LocalDateTime exitTimestamp = parse(line[2].trim());
      CaveAchievements cave = new CaveAchievements();
      cave.setNotificationTimestamp(notificationTimestamp);
      cave.setEntryTimestamp(entryTimestamp);
      cave.setExitTimestamp(compareTime(entryTimestamp, exitTimestamp));
      String caveName = line[3].trim();

      cave.setCaveName(caveName);
      cave.setReachedParts(line[4].trim());
      cave.setCaveOvercomeStyle(CaveOvercomeStyle.valueOf(line[5].trim().toUpperCase()).getType());
      cave.setCountry(line[6].trim());
      cave.setRegion(line[7].trim());
      cave.setAuthorsFromAnotherClubs(line[9].trim());
      cave.setComment(line[10].trim());
      String email = line[11].trim();
      List<Users> usersBatchList = userRepository.findAll();
      for (Users user : usersBatchList) {
        if (email.equals(user.getEmail())) {
          cave.setNotificationAuthor(user);
        }
      }
//      cave.getAuthors().add(userRepository.findById(5L).orElse(null));
      String authors = line[8].trim();
      //todo tutaj może być czytana na koncu i poczatku "";
      String[] users = authors.split(",");
      if (users[0].charAt(0) == '"') {
        users[0] = users[0].substring(1);
      }
      String last = users[users.length - 1];
      if (last.charAt(last.length() - 1) == '"') {
        users[0] = users[0].substring(0, last.length() - 2);
      }
      //nieoptymalne
      for (String s : users) {
        for (Users user : usersBatchList) {
          if (s.toUpperCase().contains(user.getSurname().toUpperCase())) {
            log.info("authors from table:" + s);
            log.info("authors from db:" + user.getSurname());
            cave.getAuthors().add(user);
          }
        }
      }
      log.info("cave {} achievement on {}", caveName, notificationTimestamp.toString());
      caveRepository.save(cave);
    }
  }

  private LocalDateTime parse(String timestamp) {
    String trimmedTimestamp = timestamp.trim();

    if ((trimmedTimestamp.charAt(0) == '\'')) {
      trimmedTimestamp = trimmedTimestamp.substring(1);
    }
    Pattern datePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
    if (datePattern.matcher(trimmedTimestamp).matches()) {
      trimmedTimestamp = trimmedTimestamp + " 00:00:00";
    }
    Pattern dateTimePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}"); // TODO check
    if (dateTimePattern.matcher(trimmedTimestamp).matches()) {
      trimmedTimestamp = trimmedTimestamp + ":00";
    }
    LocalDateTime localDateTime = LocalDateTime.parse(trimmedTimestamp, formatter);
    return LocalDateTime.parse(trimmedTimestamp, formatter);
  }

  private LocalDateTime compareTime(LocalDateTime enter, LocalDateTime exit) {
    LocalDateTime processedExit = exit;
    if (enter.equals(exit)) {
      processedExit = exit.plusHours(1);
    }
    return processedExit;
  }

  public CaveProcessor() {
  }
}
