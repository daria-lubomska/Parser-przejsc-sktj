package com.sktj.controller;

import com.sktj.controller.specification.CaveAchievFiltersSpecification;
import com.sktj.controller.specification.CaveAchievSearchSpecification;
import com.sktj.entity.CaveAchievements;
import com.sktj.model.CaveAchievModel;
import com.sktj.service.CaveAchievementsService;
import com.sktj.util.Mappings;
import java.util.List;
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

@Slf4j
@RestController
@RequestMapping(Mappings.CAVES)
public class CaveAchievsController {

  private final CaveAchievementsService service;

  @Autowired
  public CaveAchievsController(CaveAchievementsService service) {
    this.service = service;
  }

  @Transactional
  @GetMapping
  public List<CaveAchievModel> getAll() {
    return service.getAll();
  }

  @Transactional
  @GetMapping(Mappings.GET_CAVE_ACHIEV)
  public CaveAchievModel get(@PathVariable("caveAchievId") Long id) {
    return service.findById(id);
  }

  @Transactional
  @PostMapping(Mappings.ADD_NEW)
  public ResponseEntity<?> save(@Valid @RequestBody CaveAchievements achiev) {
    service.save(achiev);
    log.info("Cave achievement with notification time {}, created by {} added to DB successfully",
        achiev.getNotificationTimestamp(), achiev.getNotificationAuthor().getEmail());
    return new ResponseEntity<CaveAchievements>(HttpStatus.CREATED);
  }

  @PutMapping(Mappings.EDIT_CAVE_ACHIEV)
  public ResponseEntity<?> update(@PathVariable("caveAchievId")
      Long id, @Valid @RequestBody CaveAchievements achiev) {
    service.update(id, achiev);
    log.info("Cave achievement with id {} updated successfully", id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping(Mappings.DELETE_CAVE_ACHIEV)
  public ResponseEntity<?> delete(@PathVariable("caveAchievId") Long id) {
    service.delete(id);
    log.info("Cave achievement with id {} removed successfully", id);
    return new ResponseEntity<CaveAchievements>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(Mappings.FILTER)
  public List<CaveAchievModel> filter(CaveAchievFiltersSpecification spec,
      @PageableDefault(size = 20, sort = "entryTimestamp",
          direction = Direction.DESC) Pageable pageable) {
    return service.filter(spec, pageable);
  }

  @GetMapping(Mappings.SEARCH)
  public Iterable<CaveAchievModel> search(CaveAchievSearchSpecification spec,
      @PageableDefault(size = 20, sort = "entryTimestamp",
          direction = Direction.DESC) Pageable pageable) {
    return service.search(spec, pageable);
  }

  //TODO check after Ghost integration

  @GetMapping(Mappings.CAVES_AND_NOTIF)
  public ResponseEntity<List<CaveAchievModel>> getUserCaveAchievementsAndNotifications(
      String someGhostIntegration) {
    return ResponseEntity.ok()
        .body(service.getUserCaveAchievementsAndNotifications(someGhostIntegration));
  }
}
