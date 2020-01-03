package com.sktj.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClimbingModel {

  LocalDateTime notificationTimestamp;
  LocalDate date;
  Duration duration;
  String routeName;
  String difficultyGrade;
  String wall;
  CountryModel country;
  String region;
  Set<UserModel> authors;
  String anotherAuthors;
  String comment;
  UserModel notificationAuthor;
}
