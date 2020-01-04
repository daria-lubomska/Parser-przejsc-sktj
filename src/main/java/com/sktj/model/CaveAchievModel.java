package com.sktj.model;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaveAchievModel extends AchievModel{

  LocalDateTime entryTimestamp;
  LocalDateTime exitTimestamp;
  CaveModel caveName;
  String reachedParts;
  String caveOvercomeStyle;
}
