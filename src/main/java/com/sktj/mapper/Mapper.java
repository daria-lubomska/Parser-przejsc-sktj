package com.sktj.mapper;

import com.sktj.entity.Cave;
import com.sktj.entity.CaveAchievements;
import com.sktj.entity.ClimbingAchievements;
import com.sktj.entity.Country;
import com.sktj.entity.OtherActivityAchievements;
import com.sktj.entity.User;
import com.sktj.model.CaveAchievModel;
import com.sktj.model.CaveModel;
import com.sktj.model.ClimbingModel;
import com.sktj.model.CountryModel;
import com.sktj.model.OtherAchievModel;
import com.sktj.model.UserModel;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;


@Service
public class Mapper {

  public UserModel mapUser(User user) {
    return new UserModel(user.getName(), user.getSurname(), user.getEmail());
  }

  public CaveModel mapCave(Cave cave) {
    return new CaveModel(cave.getName(), cave.getRegion());
  }

  public CountryModel mapCountry(Country country) {
    return new CountryModel(country.getName());
  }

  public CaveAchievModel mapCaveAchiev(CaveAchievements achiev) {
    CaveAchievModel model = new CaveAchievModel();
    model.setEntryTimestamp(achiev.getEntryTimestamp());
    model.setExitTimestamp(achiev.getExitTimestamp());
    model.setNotificationTimestamp(achiev.getNotificationTimestamp());
    model.setAnotherAuthors(achiev.getAnotherAuthors());
    model.setComment(achiev.getComment());
    model.setCaveName(mapCave(achiev.getCaveName()));
    model.setCaveOvercomeStyle(achiev.getCaveOvercomeStyle());
    model.setNotificationAuthor(mapUser(achiev.getNotificationAuthor()));
    model.setCountry(mapCountry(achiev.getCountry()));
    model.setReachedParts(achiev.getReachedParts());
    Set<UserModel> authors = new HashSet<>();
    achiev.getAuthors()
        .forEach(i -> authors.add(new UserModel(i.getName(), i.getSurname(), i.getEmail())));
    model.setAuthors(authors);
    return model;
  }

  public ClimbingModel mapClimbing(ClimbingAchievements achiev){
    ClimbingModel model = new ClimbingModel();
    model.setAnotherAuthors(achiev.getAnotherAuthors());
    model.setComment(achiev.getComment());
    model.setCountry(mapCountry(achiev.getCountry()));
    model.setDate(achiev.getDate());
    model.setDifficultyGrade(achiev.getDifficultyGrade());
    model.setDuration(achiev.getDuration());
    model.setNotificationAuthor(mapUser(achiev.getNotificationAuthor()));
    model.setNotificationTimestamp(achiev.getNotificationTimestamp());
    model.setRegion(achiev.getRegion());
    model.setRouteName(achiev.getRouteName());
    model.setWall(achiev.getWall());
    Set<UserModel> authors = new HashSet<>();
    achiev.getAuthors()
        .forEach(i -> authors.add(new UserModel(i.getName(), i.getSurname(), i.getEmail())));
    model.setAuthors(authors);
    return model;
  }

  public OtherAchievModel mapOtherAchiev(OtherActivityAchievements achiev){
    OtherAchievModel model = new OtherAchievModel();
    model.setAnotherAuthors(achiev.getAnotherAuthors());
    model.setComment(achiev.getComment());
    model.setCountry(mapCountry(achiev.getCountry()));
    model.setDate(achiev.getDate());
    model.setDuration(achiev.getDuration());
    model.setNotificationAuthor(mapUser(achiev.getNotificationAuthor()));
    model.setNotificationTimestamp(achiev.getNotificationTimestamp());
    model.setRegion(achiev.getRegion());
    model.setAchievementDescription(achiev.getAchievementDescription());
    Set<UserModel> authors = new HashSet<>();
    achiev.getAuthors()
        .forEach(i -> authors.add(new UserModel(i.getName(), i.getSurname(), i.getEmail())));
    model.setAuthors(authors);
    return  model;
  }
}
