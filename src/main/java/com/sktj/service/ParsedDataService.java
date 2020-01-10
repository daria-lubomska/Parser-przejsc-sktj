package com.sktj.service;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParsedDataService {

  private final CaveAchievementParser caveParser;
  private final ClimbingAchievementParser climbingParser;
  private final UserCaveCountryParser parser;

  @Autowired
  public ParsedDataService(CaveAchievementParser caveParser,
      ClimbingAchievementParser climbingParser, UserCaveCountryParser parser) {
    this.caveParser = caveParser;
    this.climbingParser = climbingParser;
    this.parser = parser;
  }

  @PostConstruct
  public void saveDataToDB(){
    parser.saveDataToDB();
    caveParser.saveDataToDB();
    climbingParser.saveDataToDB();
  }
}
