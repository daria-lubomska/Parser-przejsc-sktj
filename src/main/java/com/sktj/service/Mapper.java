package com.sktj.service;

import com.sktj.entity.Achievement;
import com.sktj.entity.AchievementDetails;
import com.sktj.entity.Cave;
import com.sktj.entity.CaveAchievements;
import com.sktj.entity.ClimbingAchievements;
import com.sktj.entity.Country;
import com.sktj.entity.OtherActivityAchievements;
import com.sktj.entity.User;
import com.sktj.model.AchievModel;
import com.sktj.model.CaveAchievModel;
import com.sktj.model.CaveModel;
import com.sktj.model.ClimbingModel;
import com.sktj.model.CountryModel;
import com.sktj.model.DetailedAchievModel;
import com.sktj.model.OtherAchievModel;
import com.sktj.model.UserModel;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;


@Service
public class Mapper {

  UserModel mapUser(User user) {
    return new UserModel(user.getName(), user.getSurname(), user.getEmail());
  }

  CaveModel mapCave(Cave cave) {
    return new CaveModel(cave.getName(), cave.getRegion());
  }

  CountryModel mapCountry(Country country) {
    return new CountryModel(country.getName());
  }

  private void mapAchiev(Achievement achiev, AchievModel model) {
    model.setAnotherAuthors(achiev.getAnotherAuthors());
    model.setNotificationTimestamp(achiev.getNotificationTimestamp());
    model.setAnotherAuthors(achiev.getAnotherAuthors());
    model.setComment(achiev.getComment());
    model.setNotificationAuthor(mapUser(achiev.getNotificationAuthor()));
    model.setCountry(mapCountry(achiev.getCountry()));
    Set<UserModel> authors = new HashSet<>();
    achiev.getAuthors()
        .forEach(i -> authors.add(new UserModel(i.getName(), i.getSurname(), i.getEmail())));
    model.setAuthors(authors);
  }

  private void mapDetailedAchiev(AchievementDetails achiev, DetailedAchievModel model) {
    model.setDate(achiev.getDate());
    model.setDuration(achiev.getDuration());
    model.setRegion(achiev.getRegion());
    mapAchiev(achiev, model);
  }

  CaveAchievModel mapCaveAchiev(CaveAchievements achiev) {
    CaveAchievModel model = new CaveAchievModel();
    model.setEntryTimestamp(achiev.getEntryTimestamp());
    model.setExitTimestamp(achiev.getExitTimestamp());
    model.setCaveName(mapCave(achiev.getCaveName()));
    model.setCaveOvercomeStyle(achiev.getCaveOvercomeStyle());
    model.setReachedParts(achiev.getReachedParts());
    mapAchiev(achiev, model);
    return model;
  }

  ClimbingModel mapClimbing(ClimbingAchievements achiev){
    ClimbingModel model = new ClimbingModel();
    model.setDifficultyGrade(achiev.getDifficultyGrade());
    model.setRouteName(achiev.getRouteName());
    model.setWall(achiev.getWall());
    mapDetailedAchiev(achiev, model);
    return model;
  }

  OtherAchievModel mapOtherAchiev(OtherActivityAchievements achiev){
    OtherAchievModel model = new OtherAchievModel();
    model.setAchievementDescription(achiev.getAchievementDescription());
    model.setCategory(achiev.getCategory());
    mapDetailedAchiev(achiev, model);
    return  model;
  }
}
