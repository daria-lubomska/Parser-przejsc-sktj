package com.sktj.model;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AchievModel {

  LocalDateTime notificationTimestamp;
  CountryModel country;
  UserModel notificationAuthor;
  String anotherAuthors;
  String comment;
  Set<UserModel> authors;
}
