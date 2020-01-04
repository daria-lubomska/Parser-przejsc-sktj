package com.sktj.controller;

import com.sktj.entity.Achievement;
import com.sktj.entity.AchievementDetails;
import com.sktj.entity.Cave;
import com.sktj.entity.CaveAchievements;
import com.sktj.entity.ClimbingAchievements;
import com.sktj.entity.OtherActivityAchievements;
import com.sktj.entity.User;
import com.sktj.service.CaveService;
import com.sktj.service.CountryService;
import com.sktj.service.UserService;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class SaveUpdateProcessor {

  private final CountryService countryService;
  private final com.sktj.service.UserService userService;
  private final CaveService caveService;

  @Autowired
  public SaveUpdateProcessor(CountryService countryService, UserService userService,
      CaveService caveService) {
    this.countryService = countryService;
    this.userService = userService;
    this.caveService = caveService;
  }

  void saveProcess(Achievement achiev) {
    Set<User> authors = new HashSet<>();
    achiev.getAuthors().forEach(i -> authors.add(userService.findUserByEmail(i.getEmail())));
    achiev.setAuthors(authors);
    achiev.setCountry(countryService.findCountryByName(achiev.getCountry().getName()));
    achiev.setNotificationAuthor(userService.
        findUserByEmail(achiev.getNotificationAuthor().getEmail()));
  }

  void saveCaveAchievProcess(CaveAchievements achiev) {
    saveProcess(achiev);
    Cave cave = achiev.getCaveName();
    if (caveService.findByNameAndRegion(cave.getName(), cave.getRegion()) != null) {
      achiev.setCaveName(caveService.findByNameAndRegion(cave.getName(), cave.getRegion()));
    }
  }

  private void updateProcess(Achievement achiev,
      Achievement editedCaveAchiev) {
    Set<User> authors = new HashSet<>();
    achiev.getAuthors().forEach(i -> authors.add(userService.findUserByEmail(i.getEmail())));
    editedCaveAchiev.setAuthors(authors);
    editedCaveAchiev.setNotificationTimestamp(achiev.getNotificationTimestamp());
    editedCaveAchiev.setAnotherAuthors(achiev.getAnotherAuthors());
    editedCaveAchiev
        .setCountry(countryService.findCountryByName(achiev.getCountry().getName()));
    editedCaveAchiev.setNotificationAuthor(userService.
        findUserByEmail(achiev.getNotificationAuthor().getEmail()));
    editedCaveAchiev.setComment(achiev.getComment());
  }

  void updateCaveAchievProcess(CaveAchievements achiev,
      CaveAchievements editedCaveAchiev) {
    updateProcess(achiev, editedCaveAchiev);
    Cave editedCave = achiev.getCaveName();
    if (caveService.findByNameAndRegion(editedCave.getName(), editedCave.getRegion()) == null) {
      editedCaveAchiev.setCaveName(editedCave);
    } else {
      Cave cave = caveService.findByNameAndRegion(editedCave.getName(), editedCave.getRegion());
      editedCaveAchiev.setCaveName(cave);
    }
    editedCaveAchiev.setCaveOvercomeStyle(achiev.getCaveOvercomeStyle());
    editedCaveAchiev.setEntryTimestamp(achiev.getEntryTimestamp());
    editedCaveAchiev.setExitTimestamp(achiev.getExitTimestamp());
    editedCaveAchiev.setReachedParts(achiev.getReachedParts());
  }

  private void detailedSaveProcess(AchievementDetails achiev,
      AchievementDetails editedCaveAchiev) {
    updateProcess(achiev, editedCaveAchiev);
    editedCaveAchiev.setDuration(achiev.getDuration());
    editedCaveAchiev.setRegion(achiev.getRegion());
  }

  void updateClimbingAchievProcess(ClimbingAchievements achiev,
      ClimbingAchievements editedCaveAchiev) {
    detailedSaveProcess(achiev, editedCaveAchiev);
    editedCaveAchiev.setWall(achiev.getWall());
    editedCaveAchiev.setDifficultyGrade(achiev.getDifficultyGrade());
    editedCaveAchiev.setRouteName(achiev.getRouteName());
  }

  void updateOtherAchievProcess(OtherActivityAchievements achiev,
      OtherActivityAchievements editedOtherAchiev) {
    detailedSaveProcess(achiev, editedOtherAchiev);
    editedOtherAchiev.setAchievementDescription(achiev.getAchievementDescription());
    editedOtherAchiev.setCategory(achiev.getCategory());
  }
}
