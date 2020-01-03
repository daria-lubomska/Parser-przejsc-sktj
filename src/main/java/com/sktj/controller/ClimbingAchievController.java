package com.sktj.controller;

import com.sktj.controller.specification.ClimbingAchievFiltersSpecification;
import com.sktj.controller.specification.ClimbingAchievSearchSpecification;
import com.sktj.entity.CaveAchievements;
import com.sktj.entity.ClimbingAchievements;
import com.sktj.entity.User;
import com.sktj.mapper.Mapper;
import com.sktj.model.ClimbingModel;
import com.sktj.repository.ClimbingRepository;
import com.sktj.service.ClimbingService;
import com.sktj.service.CountryService;
import com.sktj.service.UserService;
import com.sktj.util.Mappings;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping(Mappings.CLIMBING)
public class ClimbingAchievController {

  private final ClimbingRepository repository;
  private final ClimbingService service;
  private final UserService userService;
  private final CountryService countryService;
  private final Mapper mapper;

  @Autowired
  public ClimbingAchievController(ClimbingRepository repository,
      ClimbingService service, UserService userService,
      CountryService countryService, Mapper mapper) {
    this.repository = repository;
    this.service = service;
    this.userService = userService;
    this.countryService = countryService;
    this.mapper = mapper;
  }

  @Transactional
  @GetMapping(Mappings.GET_CLIMBING)
  public ResponseEntity<?> get(@PathVariable("climbingId") Long climbingId) {
    ClimbingAchievements achiev = repository.findById(climbingId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Climbing Not Found"));
    log.info("Climbing achievement with id {} fetched", climbingId);
    return ResponseEntity.ok(mapper.mapClimbing(achiev));
  }

  @PostMapping(Mappings.ADD_NEW)
  public ResponseEntity<?> save(@Valid @RequestBody ClimbingAchievements achiev) {
    Set<User> authors = new HashSet<>();
    achiev.getAuthors().forEach(i -> authors.add(userService.findUserByEmail(i.getEmail())));
    achiev.setAuthors(authors);
    achiev.setCountry(countryService.findCountryByName(achiev.getCountry().getName()));
    achiev.setNotificationAuthor(userService.
        findUserByEmail(achiev.getNotificationAuthor().getEmail()));
    repository.save(achiev);
    log.info("Climbing achievement with notification time {}, created by {} saved successfully",
        achiev.getNotificationTimestamp(), achiev.getNotificationAuthor().getEmail());
    return new ResponseEntity<CaveAchievements>(HttpStatus.CREATED);
  }

  @PutMapping(Mappings.EDIT_CLIMBING)
  public ResponseEntity<ClimbingAchievements> update(@PathVariable("climbingId")
      Long climbingId, @Valid @RequestBody ClimbingAchievements achiev) {
    ClimbingAchievements editedClimbingAcheiv = repository.findById(climbingId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Climbing Achievement Not Found"));
    editedClimbingAcheiv.setDuration(achiev.getDuration());
    editedClimbingAcheiv.setAnotherAuthors(achiev.getAnotherAuthors());
    editedClimbingAcheiv.setCountry(countryService.findCountryByName(achiev.getCountry().getName()));
    editedClimbingAcheiv.setNotificationAuthor(userService.
        findUserByEmail(achiev.getNotificationAuthor().getEmail()));
    editedClimbingAcheiv.setRegion(achiev.getRegion());
    editedClimbingAcheiv.setWall(achiev.getWall());
    editedClimbingAcheiv.setDifficultyGrade(achiev.getDifficultyGrade());
    editedClimbingAcheiv.setRouteName(achiev.getRouteName());
    editedClimbingAcheiv.setDate(achiev.getDate());
    editedClimbingAcheiv.setNotificationTimestamp(achiev.getNotificationTimestamp());
    editedClimbingAcheiv.setComment(achiev.getComment());
    Set<User> authors = new HashSet<>();
    achiev.getAuthors().forEach(i -> authors.add(userService.findUserByEmail(i.getEmail())));
    editedClimbingAcheiv.setAuthors(authors);
    repository.save(editedClimbingAcheiv);
    log.info("Climbing achievement with id {} updated successfully", climbingId);
    return ResponseEntity.ok(editedClimbingAcheiv);
  }

  @DeleteMapping(Mappings.DELETE_CLIMBING)
  public ResponseEntity<?> delete(@PathVariable("climbingId") Long climbingId) {
    ClimbingAchievements achiev = repository.findById(climbingId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Climbing Achievement Not Found"));
    repository.delete(achiev);
    log.info("Climbing achievement with id {} removed successfully", climbingId);
    return new ResponseEntity<ClimbingAchievements>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(Mappings.FILTER)
  public Iterable<ClimbingModel> filter(ClimbingAchievFiltersSpecification spec,
      @PageableDefault(size = 20, sort = "date",
          direction = Direction.DESC) Pageable pageable) {
    List<ClimbingModel> model = new ArrayList<>();
    repository.findAll(spec, pageable).forEach(i -> model.add(mapper.mapClimbing(i)));
    return model;
  }

  @GetMapping(Mappings.SEARCH)
  public Iterable<ClimbingModel> search(ClimbingAchievSearchSpecification spec,
      @PageableDefault(size = 20, sort = "entryTimestamp",
          direction = Direction.DESC) Pageable pageable) {
    List<ClimbingModel> model = new ArrayList<>();
    repository.findAll(spec, pageable).forEach(i -> model.add(mapper.mapClimbing(i)));
    return model;
  }

  //TODO check after Ghost integration

  @GetMapping(Mappings.CLIMBING_AND_NOTIF)
  public ResponseEntity<List<ClimbingModel>> getUserClimbingAchievementsAndNotifications(
      String someGhostIntegration) {
    List<ClimbingModel> model = new ArrayList<>();
    service.findUsersClimbingAchievs(someGhostIntegration)
        .forEach(i -> model.add(mapper.mapClimbing(i)));
    return ResponseEntity.ok().body(model);
  }
}
