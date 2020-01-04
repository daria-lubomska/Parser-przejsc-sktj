package com.sktj.controller;

import com.sktj.controller.specification.OtherAchievFiltersSpecification;
import com.sktj.controller.specification.OtherAchievSearchSpecification;
import com.sktj.entity.OtherActivityAchievements;
import com.sktj.model.OtherAchievModel;
import com.sktj.repository.OtherAchievRepository;
import com.sktj.service.Mapper;
import com.sktj.service.OtherService;
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
@RequestMapping(Mappings.OTHERS)
public class OtherAchievsController {

  private final OtherAchievRepository repository;
  private final OtherService service;
  private final SaveUpdateProcessor processor;
  private final Mapper mapper;

  @Autowired
  public OtherAchievsController(OtherAchievRepository repository,
      OtherService service, SaveUpdateProcessor processor, Mapper mapper) {
    this.repository = repository;
    this.service = service;
    this.processor = processor;
    this.mapper = mapper;
  }

  @Transactional
  @GetMapping(Mappings.GET_OTHER)
  public OtherAchievModel get(@PathVariable("otherId") Long otherId) {
    OtherActivityAchievements achiev = repository.findById(otherId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Other Activities Achievement Not Found"));
    log.info("Other activity achievement with id {} fetched", otherId);
    return mapper.mapOtherAchiev(achiev);
  }

  @Transactional
  @PostMapping(Mappings.ADD_NEW)
  public ResponseEntity<?> save(@Valid @RequestBody OtherActivityAchievements achiev) {
    processor.saveProcess(achiev);
    repository.save(achiev);
    log.info(
        "Other activity achievement with notification time {}, created by {} saved successfully",
        achiev.getNotificationTimestamp(),
        achiev.getNotificationAuthor().getEmail());
    return new ResponseEntity<OtherActivityAchievements>(HttpStatus.CREATED);
  }

  @PutMapping(Mappings.EDIT_OTHER_ACHIEV)
  public ResponseEntity<OtherActivityAchievements> update(@PathVariable("otherId")
      Long otherId, @Valid @RequestBody OtherActivityAchievements achiev) {
    OtherActivityAchievements editedAchiev = repository.findById(otherId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Other Activities Achievement Not Found"));
    processor.updateOtherAchievProcess(achiev, editedAchiev);
    repository.save(editedAchiev);
    log.info("Other activity achievement with id {} updated successfully", otherId);
    return ResponseEntity.ok(editedAchiev);
  }

  @DeleteMapping(Mappings.DELETE_OTHER_ACHIEV)
  public ResponseEntity<?> delete(@PathVariable("otherId") Long otherId) {
    OtherActivityAchievements editedAchiev = repository.findById(otherId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Other Activities Achievement Not Found"));
    repository.delete(editedAchiev);
    log.info("Other activity achievement with id  {} removed successfully", otherId);
    return new ResponseEntity<OtherActivityAchievements>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(Mappings.FILTER)
  public Iterable<OtherAchievModel> filter(
      OtherAchievFiltersSpecification spec,
      @PageableDefault(size = 20, sort = "date",
          direction = Direction.DESC) Pageable pageable) {
    List<OtherAchievModel> model = new ArrayList<>();
    repository.findAll(spec, pageable).forEach(i -> model.add(mapper.mapOtherAchiev(i)));
    return model;
  }

  @GetMapping(Mappings.SEARCH)
  public Iterable<OtherAchievModel> search(
      OtherAchievSearchSpecification spec,
      @PageableDefault(size = 20, sort = "date",
          direction = Direction.DESC) Pageable pageable) {
    List<OtherAchievModel> model = new ArrayList<>();
    repository.findAll(spec, pageable).forEach(i -> model.add(mapper.mapOtherAchiev(i)));
    return model;
  }

  //TODO check after Ghost integration

  @GetMapping(Mappings.OTHERS_AND_NOTIF)
  public ResponseEntity<List<OtherAchievModel>> getUserOtherAchievementsAndNotifications(
      String someGhostIntegration) {
    List<OtherAchievModel> model = new ArrayList<>();
    service.findUsersOtherAchievs(someGhostIntegration)
        .forEach(i -> model.add(mapper.mapOtherAchiev(i)));
    return ResponseEntity.ok().body(model);
  }
}
