package com.sktj.controller;

import com.sktj.configuration.AppProperties;
import com.sktj.entity.CaveAchievements;
import com.sktj.entity.ClimbingAchievements;
import com.sktj.entity.OtherActivityAchievements;
import com.sktj.entity.User;
import com.sktj.exception.ForbiddenActionExeption;
import com.sktj.repository.UserRepository;
import com.sktj.util.Mappings;
import java.util.Set;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping(Mappings.LIVE_USERS)
public class UserController {

  private final UserRepository repository;
  private final AppProperties properties;

  @Autowired
  public UserController(UserRepository repository, AppProperties properties) {
    this.repository = repository;
    this.properties = properties;
  }

  @GetMapping
  public Iterable<User> getAll() {
    return repository.findAll();
  }

  @GetMapping(Mappings.USER_ID)
  public ResponseEntity<User> getUser(@PathVariable(value = "userId") Long userId) {
    User user = repository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
    log.info("User with id {} fetched", userId);
    return ResponseEntity.ok(user);
  }

  @PostMapping(Mappings.ADD_NEW)
  public ResponseEntity<?> save(@Valid @RequestBody User user)
      throws ForbiddenActionExeption {
    if(!user.getRole().equals("user")){
      throw new ForbiddenActionExeption("Validation exception" + HttpStatus.FORBIDDEN);
    }
    repository.save(user);
    log.info("User {} {} saved successfully", user.getSurname(), user.getName());
    return new ResponseEntity<User>(HttpStatus.CREATED);
  }

  @PutMapping(Mappings.EDIT_USER)
  public ResponseEntity<User> update(@PathVariable("userId") long userId,
      @Valid @RequestBody User user) {

    User editedUser = repository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
    if (!editedUser.getEmail().equals(properties.getSuperAdminEmail())){
      editedUser.setName(user.getName());
      editedUser.setSurname(user.getSurname());
      editedUser.setCardNumber(editedUser.getCardNumber());
      editedUser.setEmail(user.getEmail());
      editedUser.setCaveNotifications(user.getCaveNotifications());
      editedUser.setCaves(user.getCaves());
      editedUser.setClimbing(user.getClimbing());
      editedUser.setClimbingNotifications(user.getClimbingNotifications());
      editedUser.setOthers(user.getOthers());
      editedUser.setOtherNotifications(user.getOtherNotifications());
      repository.save(editedUser);
      log.info("User with id {} updated successfully", userId);
    }
    return ResponseEntity.ok(editedUser);
  }
  @PatchMapping(Mappings.GRANT_ADMIN)
  public ResponseEntity<User> grantAdminPermissions(@PathVariable("userId") long userId) {
    User user = repository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
    if (!user.getEmail().equals(properties.getSuperAdminEmail())){
      user.setRole("admin");
      repository.save(user);
      log.info("Admin permissions granted for User with id {} ", userId);
    }
    return ResponseEntity.ok(user);
  }


  @DeleteMapping(Mappings.DELETE_USER)
  public ResponseEntity<?> delete(@PathVariable("userId") Long userId) {
    User user = repository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
    if (!user.getEmail().equals(properties.getSuperAdminEmail())){
      repository.delete(user);
      log.info("User with id {} removed successfully", userId);
    }
    return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
  }

  //TODO integration with Ghost - HttpSession attributes (name, surname, email) currently logged user?
  // now this method is not working -FIX after integration

  @GetMapping(Mappings.CAVES_AND_NOTIF)
  public ResponseEntity<Set<CaveAchievements>> getUserCaveAchievementsAndNotifications(HttpSession session) {
    User user = repository.findUserByEmail(session.getAttribute("email").toString());
    Set<CaveAchievements> achievementsAndNotifications = user.getCaves();
    achievementsAndNotifications.addAll(user.getCaveNotifications());
    return ResponseEntity.ok().body(achievementsAndNotifications);
  }

  @GetMapping(Mappings.CLIMBING_AND_NOTIF)
  public ResponseEntity<Set<ClimbingAchievements>> getUserClimbingAchievementsAndNotifications(HttpSession session) {
    User user = repository.findUserByEmail(session.getAttribute("email").toString());
    Set<ClimbingAchievements> achievementsAndNotifications = user.getClimbing();
    achievementsAndNotifications.addAll(user.getClimbingNotifications());
    return ResponseEntity.ok().body(achievementsAndNotifications);
  }

  @GetMapping(Mappings.OTHERS_AND_NOTIF)
  public ResponseEntity<Set<OtherActivityAchievements>> getUserOtherAchievementsAndNotifications(HttpSession session) {
    User user = repository.findUserByEmail(session.getAttribute("email").toString());
    Set<OtherActivityAchievements> achievementsAndNotifications = user.getOthers();
    achievementsAndNotifications.addAll(user.getOtherNotifications());
    return ResponseEntity.ok(achievementsAndNotifications);
  }
}
