package com.sktj.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtherAchievModel {

  LocalDateTime notificationTimestamp;
  LocalDate date;
  Duration duration;
  String region;
  String achievementDescription;
  String category; //TODO enum?
  CountryModel country;
  UserModel notificationAuthor;
  Set<UserModel> authors;
  String anotherAuthors;
  String comment;
}
