package com.sktj.model;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaveAchievModel {

  LocalDateTime notificationTimestamp;
  LocalDateTime entryTimestamp;
  LocalDateTime exitTimestamp;
  CountryModel country;
  CaveModel caveName;
  UserModel notificationAuthor;
  Set<UserModel> authors;
  String reachedParts;
  String caveOvercomeStyle;
  String anotherAuthors;
  String comment;
}
