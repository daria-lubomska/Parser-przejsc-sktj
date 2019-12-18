package com.sktj.controller;

import com.sktj.configuration.AppProperties;
import com.sktj.entity.CaveAchievements;
import com.sktj.entity.ClimbingAchievements;
import com.sktj.entity.OtherActivityAchievements;
import com.sktj.entity.User;
import com.sktj.exception.ForbiddenActionExeption;
import com.sktj.exception.ResourceNotFoundExeption;
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

@Slf4j
@RestController
@RequestMapping(Mappings.LIVE_USERS)
public class UserController {

  private final UserRepository userRepository;
  private final AppProperties appProperties;

  @Autowired
  public UserController(UserRepository userRepository, AppProperties appProperties) {
    this.userRepository = userRepository;
    this.appProperties = appProperties;
  }

  @GetMapping
  public Iterable<User> getAll() {
    return userRepository.findAll();
  }

  @GetMapping(Mappings.USER_ID)
  public ResponseEntity<User> getUsersById(@PathVariable(value = "userId") Long userId)
      throws ResourceNotFoundExeption {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundExeption("User with id "
            + userId + " not found" + HttpStatus.NOT_FOUND));
    log.info("User with id {} fetched", userId);
    return ResponseEntity.ok(user);
  }

  @PostMapping(Mappings.ADD_USER)
  public ResponseEntity<?> createUser(@Valid @RequestBody User user)
      throws ForbiddenActionExeption {
    if(!user.getRole().equals("user")){
      throw new ForbiddenActionExeption("Validation exeption" + HttpStatus.FORBIDDEN);
    }
    userRepository.save(user);
    log.info("User {} created successfully", user.getSurname());
    return new ResponseEntity<User>(HttpStatus.CREATED);
  }

  @PutMapping(Mappings.EDIT_USER)
  public ResponseEntity<User> updateUser(@PathVariable("userId") long userId,
      @Valid @RequestBody User user) throws ResourceNotFoundExeption {

    User userToEdit = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundExeption("Unable to delete. User with id "
            + userId + " not found" + HttpStatus.NOT_FOUND));

    if (!userToEdit.getEmail().equals(appProperties.getSuperAdminEmail())){
      userToEdit.setName(user.getName());
      userToEdit.setSurname(user.getSurname());
      userToEdit.setCardNumber(userToEdit.getCardNumber());
      userToEdit.setEmail(user.getEmail());
      userToEdit.setCaveNotifications(user.getCaveNotifications());
      userToEdit.setCaves(user.getCaves());
      userToEdit.setClimbing(user.getClimbing());
      userToEdit.setClimbingNotifications(user.getClimbingNotifications());
      userToEdit.setOthers(user.getOthers());
      userToEdit.setOtherNotifications(user.getOtherNotifications());
      userRepository.save(userToEdit);
      log.info("User with id {} updated successfully", userId);
    }
    return ResponseEntity.ok(userToEdit);
  }
  @PatchMapping(Mappings.GRANT_ADMIN)
  public ResponseEntity<User> grantAdminPermissions(@PathVariable("userId") long userId)
      throws ResourceNotFoundExeption {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundExeption("Unable to edit. User with id "
            + userId + " not found" + HttpStatus.NOT_FOUND));
    if (!user.getEmail().equals(appProperties.getSuperAdminEmail())){
      user.setRole("admin");
      userRepository.save(user);
      log.info("Admin permissions granted for User with id {} ", userId);
    }
    return ResponseEntity.ok(user);
  }


  @DeleteMapping(Mappings.DELETE_USER)
  public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) throws ResourceNotFoundExeption {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundExeption("Unable to delete. User with id "
            + userId + " not found" + HttpStatus.NOT_FOUND));
    if (!user.getEmail().equals(appProperties.getSuperAdminEmail())){
      userRepository.delete(user);
      log.info("User with id {} removed successfully", userId);
    }
    return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
  }

  //TODO integration with Ghost - HttpSession attributes (name, surname, email) currently logged user?
  // now this method is not working -FIX after integration

  @GetMapping(Mappings.CAVES_AND_NOTIF)
  public ResponseEntity<Set<CaveAchievements>> getUserCaveAchievementsAndNotifications(HttpSession session) {
    User user = userRepository.findUserByEmail(session.getAttribute("email").toString());
    Set<CaveAchievements> achievementsAndNotifications = user.getCaves();
    achievementsAndNotifications.addAll(user.getCaveNotifications());
    return ResponseEntity.ok().body(achievementsAndNotifications);
  }

  @GetMapping(Mappings.CLIMBING_AND_NOTIF)
  public ResponseEntity<Set<ClimbingAchievements>> getUserClimbingAchievementsAndNotifications(HttpSession session) {
    User user = userRepository.findUserByEmail(session.getAttribute("email").toString());
    Set<ClimbingAchievements> achievementsAndNotifications = user.getClimbing();
    achievementsAndNotifications.addAll(user.getClimbingNotifications());
    return ResponseEntity.ok().body(achievementsAndNotifications);
  }

  @GetMapping(Mappings.OTHERS_AND_NOTIF)
  public ResponseEntity<Set<OtherActivityAchievements>> getUserOtherAchievementsAndNotifications(HttpSession session) {
    User user = userRepository.findUserByEmail(session.getAttribute("email").toString());
    Set<OtherActivityAchievements> achievementsAndNotifications = user.getOthers();
    achievementsAndNotifications.addAll(user.getOtherNotifications());
    return ResponseEntity.ok(achievementsAndNotifications);
  }
}
