package com.sktj.controller;

import com.sktj.configuration.AppProperties;
import com.sktj.entity.CaveAchievements;
import com.sktj.entity.ClimbingAchievements;
import com.sktj.entity.OtherActivityAchievements;
import com.sktj.entity.User;
import com.sktj.repository.UserRepository;
import java.util.Set;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
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

  //TODO integration with Ghost - HttpSession attributes (name, surname, email) currently logged user?
  // now this method is not working -FIX after integration
  @GetMapping("/cavesAndNotific")
  public Set<CaveAchievements> getUserCaveAchievementsAndNotifications(HttpSession session) {
    User user = userRepository.findUserByEmail(session.getAttribute("email").toString());
    Set<CaveAchievements> achievementsAndNotifications = user.getCaves();
    achievementsAndNotifications.addAll(user.getCaveNotifications());
    return achievementsAndNotifications;
  }

  @GetMapping("/climbingAndNotific")
  public Set<ClimbingAchievements> getUserClimbingAchievementsAndNotifications(HttpSession session) {
    User user = userRepository.findUserByEmail(session.getAttribute("email").toString());
    Set<ClimbingAchievements> achievementsAndNotifications = user.getClimbing();
    achievementsAndNotifications.addAll(user.getClimbingNotifications());
    return achievementsAndNotifications;
  }

  @GetMapping("/otherAndNotific")
  public Set<OtherActivityAchievements> getUserOtherAchievementsAndNotifications(HttpSession session) {
    User user = userRepository.findUserByEmail(session.getAttribute("email").toString());
    Set<OtherActivityAchievements> achievementsAndNotifications = user.getOthers();
    achievementsAndNotifications.addAll(user.getOtherNotifications());
    return achievementsAndNotifications;
  }
  
  @DeleteMapping("/{userId}")
  public String deleteUser(@PathVariable("userId") Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("This user does not exist!"));
    if (!user.getEmail().equals(appProperties.getSuperAdminEmail()))
      userRepository.delete(user);
    return "redirect:/users";
  }
}
