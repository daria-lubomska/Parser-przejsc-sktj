package com.sktj.controller;

import com.sktj.controller.specification.CaveAchievFiltersSpecification;
import com.sktj.controller.specification.CaveAchievSearchSpecification;
import com.sktj.entity.CaveAchievements;
import com.sktj.model.CaveAchievModel;
import com.sktj.repository.CaveAchievementsRepository;
import com.sktj.service.CaveAchievementsService;
import com.sktj.service.Mapper;
import com.sktj.util.Mappings;
import java.util.ArrayList;
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
  private final SaveUpdateProcessor processor;
  private final Mapper mapper;

  @Autowired
  public CaveAchievsController(CaveAchievementsRepository repository,
      CaveAchievementsService service, SaveUpdateProcessor processor, Mapper mapper) {
    this.repository = repository;
    this.service = service;
    this.processor = processor;
    this.mapper = mapper;
  }

  @Transactional
  @GetMapping(Mappings.GET_CAVE_ACHIEV)
  public CaveAchievModel get(@PathVariable("caveAchievId") Long caveAchievId) {
    CaveAchievements achiev = repository.findById(caveAchievId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Cave achievement Not Found"));
    log.info("Cave achievement with id {} fetched", caveAchievId);
    return mapper.mapCaveAchiev(achiev);
  }

  @Transactional
  @PostMapping(Mappings.ADD_NEW)
  public ResponseEntity<?> save(@Valid @RequestBody CaveAchievements achiev) {
    processor.saveCaveAchievProcess(achiev);
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
    processor.updateCaveAchievProcess(caveAchiev, editedCaveAchiev);
    repository.save(editedCaveAchiev);
    log.info("Cave achievement with id {} updated successfully", caveAchievId);
    return ResponseEntity.ok(editedCaveAchiev);
  }

  @DeleteMapping(Mappings.DELETE_CAVE_ACHIEV)
  public ResponseEntity<?> delete(@PathVariable("caveAchievId") Long caveAchievId) {
    CaveAchievements caveAchiev = repository.findById(caveAchievId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Cave Achievement Not Found"));
    repository.delete(caveAchiev);
    log.info("Cave achievement with id {} removed successfully",caveAchievId);
    return new ResponseEntity<CaveAchievements>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(Mappings.FILTER)
  public Iterable<CaveAchievModel> filter(CaveAchievFiltersSpecification spec,
      @PageableDefault(size = 20, sort = "entryTimestamp",
          direction = Direction.DESC) Pageable pageable) {
    List<CaveAchievModel> model = new ArrayList<>();
    repository.findAll(spec,pageable).forEach(i-> model.add(mapper.mapCaveAchiev(i)));
    return model;
  }

  @GetMapping(Mappings.SEARCH)
  public Iterable<CaveAchievModel> search(CaveAchievSearchSpecification spec,
      @PageableDefault(size = 20, sort = "entryTimestamp",
          direction = Direction.DESC) Pageable pageable) {
    List<CaveAchievModel> model = new ArrayList<>();
    repository.findAll(spec,pageable).forEach(i-> model.add(mapper.mapCaveAchiev(i)));
    return model;
  }

  //TODO check after Ghost integration

  @GetMapping(Mappings.CAVES_AND_NOTIF)
  public ResponseEntity<List<CaveAchievModel>> getUserCaveAchievementsAndNotifications(
      String someGhostIntegration) {
    List<CaveAchievModel> model = new ArrayList<>();
    service.findUsersCaveAchievs(someGhostIntegration)
        .forEach(i -> model.add(mapper.mapCaveAchiev(i)));
    return ResponseEntity.ok().body(model);
  }
}
