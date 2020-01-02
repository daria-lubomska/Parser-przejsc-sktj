package com.sktj.controller;

import com.sktj.controller.specification.CaveAchievFiltersSpecification;
import com.sktj.controller.specification.CaveAchievSearchSpecification;
import com.sktj.entity.CaveAchievements;
import com.sktj.repository.CaveAchievementsRepository;
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
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping(Mappings.CAVES)
public class CaveAchievsController {

  private final CaveAchievementsRepository repository;
  private final CaveAchievementsService service;

  @Autowired
  public CaveAchievsController(CaveAchievementsRepository repository,
      CaveAchievementsService service) {
    this.repository = repository;
    this.service = service;
  }

  @GetMapping(Mappings.GET_ALL)
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok(service.getAll());
  }

  @GetMapping(Mappings.GET_CAVE_ACHIEV)
  public ResponseEntity<?> get(@PathVariable("caveAchievId") Long caveAchievId) {
    CaveAchievements achiev = repository.findById(caveAchievId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Cave achievement Not Found"));
    log.info("Cave achievement with id {} fetched", caveAchievId);
    return ResponseEntity.ok(achiev);
  }

  @PostMapping(Mappings.ADD_NEW)
  public ResponseEntity<?> save(@Valid @RequestBody CaveAchievements achiev) {
    repository.save(achiev);
    log.info("Cave achievement with notification time {}, created by {} added to DB successfully",
        achiev.getNotificationTimestamp(), achiev.getNotificationAuthor().getEmail());
    return new ResponseEntity<CaveAchievements>(HttpStatus.CREATED);
  }

  @PutMapping(Mappings.EDIT_CAVE_ACHIEV)
  public ResponseEntity<CaveAchievements> update(@PathVariable("caveAchievId")
      Long caveAchievId, @Valid @RequestBody CaveAchievements caveAchiev) {
    CaveAchievements editedCaveAchiev = repository.findById(caveAchievId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Cave Achievement Not Found"));
    repository.delete(editedCaveAchiev);
    editedCaveAchiev.setNotificationTimestamp(caveAchiev.getNotificationTimestamp());
    editedCaveAchiev.setCaveName(caveAchiev.getCaveName());
    editedCaveAchiev.setAnotherAuthors(caveAchiev.getAnotherAuthors());
    editedCaveAchiev.setAuthors(caveAchiev.getAuthors());
    editedCaveAchiev.setCaveOvercomeStyle(caveAchiev.getCaveOvercomeStyle());
    editedCaveAchiev.setComment(caveAchiev.getComment());
    editedCaveAchiev.setCountry(caveAchiev.getCountry());
    editedCaveAchiev.setEntryTimestamp(caveAchiev.getEntryTimestamp());
    editedCaveAchiev.setExitTimestamp(caveAchiev.getExitTimestamp());
    editedCaveAchiev.setReachedParts(caveAchiev.getReachedParts());
    editedCaveAchiev.setNotificationAuthor(caveAchiev.getNotificationAuthor());
    repository.save(editedCaveAchiev);
    log.info("Cave achievement with id {} updated successfully", caveAchievId);
    return ResponseEntity.ok(editedCaveAchiev);
  }

  @DeleteMapping(Mappings.DELETE_CAVE_ACHIEV)
  public ResponseEntity<?> delete(@PathVariable("caveAchievId") Long caveAchievId) {
    CaveAchievements caveAchiev = service.find(caveAchievId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Cave Achievement Not Found"));
    repository.delete(caveAchiev);
    log.info("Cave achievement with id {} removed successfully",caveAchievId);
    return new ResponseEntity<CaveAchievements>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(Mappings.FILTER)
  public Iterable<CaveAchievements> filter(CaveAchievFiltersSpecification spec,
      @PageableDefault(size = 20, sort = "entryTimestamp",
          direction = Direction.DESC) Pageable pageable) {
    return repository.findAll(spec,pageable);
  }

  @GetMapping(Mappings.SEARCH)
  public Iterable<CaveAchievements> search(CaveAchievSearchSpecification spec,
      @PageableDefault(size = 20, sort = "entryTimestamp",
          direction = Direction.DESC) Pageable pageable) {
    return repository.findAll(spec,pageable);
  }

  @Transactional
  @GetMapping(Mappings.CAVES_AND_NOTIF)
  public ResponseEntity<List<CaveAchievements>> getUserCaveAchievementsAndNotifications(
      String someGhostIntegration) {
    List<CaveAchievements> achievementsAndNotifications = service
        .findUsersCaveAchievs(someGhostIntegration);
    return ResponseEntity.ok().body(achievementsAndNotifications);
  }
}
