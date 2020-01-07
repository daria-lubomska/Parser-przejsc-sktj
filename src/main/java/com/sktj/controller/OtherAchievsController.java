package com.sktj.controller;

import com.sktj.controller.specification.OtherAchievFiltersSpecification;
import com.sktj.controller.specification.OtherAchievSearchSpecification;
import com.sktj.entity.OtherActivityAchievements;
import com.sktj.model.OtherAchievModel;
import com.sktj.service.OtherService;
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
@RequestMapping(Mappings.OTHERS)
public class OtherAchievsController {

  private final OtherService service;

  @Autowired
  public OtherAchievsController(OtherService service) {
    this.service = service;
  }

  @Transactional
  @GetMapping
  public List<OtherAchievModel> getAll() {
    return service.getAll();
  }

  @Transactional
  @GetMapping(Mappings.GET_OTHER)
  public OtherAchievModel get(@PathVariable("otherId") Long id) {
    log.info("Other activity achievement with id {} fetched", id);
    return service.get(id);
  }

  @Transactional
  @PostMapping(Mappings.ADD_NEW)
  public ResponseEntity<?> save(@Valid @RequestBody OtherActivityAchievements achiev) {
    service.save(achiev);
    log.info(
        "Other activity achievement with notification time {}, created by {} saved successfully",
        achiev.getNotificationTimestamp(),
        achiev.getNotificationAuthor().getEmail());
    return new ResponseEntity<OtherActivityAchievements>(HttpStatus.CREATED);
  }

  @PutMapping(Mappings.EDIT_OTHER_ACHIEV)
  public ResponseEntity<OtherActivityAchievements> update(@PathVariable("otherId")
      Long id, @Valid @RequestBody OtherActivityAchievements achiev) {
    service.update(id, achiev);
    log.info("Other activity achievement with id {} updated successfully", id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping(Mappings.DELETE_OTHER_ACHIEV)
  public ResponseEntity<?> delete(@PathVariable("otherId") Long id) {
    service.delete(id);
    log.info("Other activity achievement with id  {} removed successfully", id);
    return new ResponseEntity<OtherActivityAchievements>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(Mappings.FILTER)
  public Iterable<OtherAchievModel> filter(
      OtherAchievFiltersSpecification spec, Pageable pageable) {
    return service.filter(spec, pageable);
  }

  @GetMapping(Mappings.SEARCH)
  public Iterable<OtherAchievModel> search(
      OtherAchievSearchSpecification spec, Pageable pageable) {
    return service.search(spec, pageable);
  }

  //TODO check after Ghost integration

  @GetMapping(Mappings.OTHERS_AND_NOTIF)
  public ResponseEntity<List<OtherAchievModel>> getUserOtherAchievementsAndNotifications(
      String someGhostIntegration) {
    return ResponseEntity.ok()
        .body(service.getUserOtherAchievementsAndNotifications(someGhostIntegration));
  }
}
