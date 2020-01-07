package com.sktj.controller;

import com.sktj.controller.specification.ClimbingAchievFiltersSpecification;
import com.sktj.controller.specification.ClimbingAchievSearchSpecification;
import com.sktj.entity.CaveAchievements;
import com.sktj.entity.ClimbingAchievements;
import com.sktj.model.ClimbingModel;
import com.sktj.service.ClimbingService;
import com.sktj.util.Mappings;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

@Slf4j
@RestController
@RequestMapping(Mappings.CLIMBING)
public class ClimbingAchievController {

  private final ClimbingService service;

  @Autowired
  public ClimbingAchievController(ClimbingService service) {
    this.service = service;
  }

  @Transactional
  @GetMapping
  public List<ClimbingModel> getAll() {
    return service.getAll();
  }

  @Transactional
  @GetMapping(Mappings.GET_CLIMBING)
  public ClimbingModel get(@PathVariable("climbingId") Long id) {
    log.info("Climbing achievement with id {} fetched", id);
    return service.get(id);
  }

  @Transactional
  @PostMapping(Mappings.ADD_NEW)
  public ResponseEntity<?> save(@Valid @RequestBody ClimbingAchievements achiev) {
    service.save(achiev);
    log.info("Climbing achievement with notification time {}, created by {} saved successfully",
        achiev.getNotificationTimestamp(), achiev.getNotificationAuthor().getEmail());
    return new ResponseEntity<CaveAchievements>(HttpStatus.CREATED);
  }

  @PutMapping(Mappings.EDIT_CLIMBING)
  public ResponseEntity<ClimbingAchievements> update(@PathVariable("climbingId")
      Long id, @Valid @RequestBody ClimbingAchievements achiev) {
    service.update(id, achiev);
    log.info("Climbing achievement with id {} updated successfully", id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping(Mappings.DELETE_CLIMBING)
  public ResponseEntity<?> delete(@PathVariable("climbingId") Long id) {
    service.delete(id);
    log.info("Climbing achievement with id {} removed successfully", id);
    return new ResponseEntity<ClimbingAchievements>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(Mappings.FILTER)
  public List<ClimbingModel> filter(ClimbingAchievFiltersSpecification spec, Pageable pageable) {
    return service.filter(spec, pageable);
  }

  @GetMapping(Mappings.SEARCH)
  public List<ClimbingModel> search(ClimbingAchievSearchSpecification spec, Pageable pageable) {
    return service.search(spec, pageable);
  }

  //TODO check after Ghost integration

  @GetMapping(Mappings.CLIMBING_AND_NOTIF)
  public ResponseEntity<List<ClimbingModel>> getUserClimbingAchievementsAndNotifications(
      String someGhostIntegration) {
    return ResponseEntity.ok()
        .body(service.getUserClimbingAchievementsAndNotifications(someGhostIntegration));
  }
}
