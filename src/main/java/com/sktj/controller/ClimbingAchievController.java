package com.sktj.controller;

import com.sktj.controller.specification.ClimbingAchievFiltersSpecification;
import com.sktj.controller.specification.ClimbingAchievSearchSpecification;
import com.sktj.entity.CaveAchievements;
import com.sktj.entity.ClimbingAchievements;
import com.sktj.repository.ClimbingRepository;
import com.sktj.util.Mappings;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(Mappings.CLIMBINGS)
public class ClimbingAchievController {

  private final ClimbingRepository repository;

  @Autowired
  public ClimbingAchievController(ClimbingRepository repository) {
    this.repository = repository;
  }


  @PostMapping(Mappings.ADD_NEW)
  public ResponseEntity<?> save(@Valid @RequestBody ClimbingAchievements achiev) {
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
    repository.delete(editedClimbingAcheiv);
    editedClimbingAcheiv.setDuration(achiev.getDuration());
    editedClimbingAcheiv.setAnotherAuthors(achiev.getAnotherAuthors());
    editedClimbingAcheiv.setCountry(achiev.getCountry());
    editedClimbingAcheiv.setRegion(achiev.getRegion());
    editedClimbingAcheiv.setWall(achiev.getWall());
    editedClimbingAcheiv.setDifficultyGrade(achiev.getDifficultyGrade());
    editedClimbingAcheiv.setRouteName(achiev.getRouteName());
    editedClimbingAcheiv.setDate(achiev.getDate());
    editedClimbingAcheiv.setNotificationTimestamp(achiev.getNotificationTimestamp());
    editedClimbingAcheiv.setNotificationAuthor(achiev.getNotificationAuthor());
    editedClimbingAcheiv.setComment(achiev.getComment());
    editedClimbingAcheiv.setAuthors(achiev.getAuthors());
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
  public Iterable<ClimbingAchievements> filter(ClimbingAchievFiltersSpecification spec,
      @PageableDefault(size = 20, sort = "date",
          direction = Direction.DESC) Pageable pageable) {
    return repository.findAll(spec, pageable);
  }

  @GetMapping(Mappings.SEARCH)
  public Iterable<ClimbingAchievements> search(ClimbingAchievSearchSpecification spec,
      @PageableDefault(size = 20, sort = "entryTimestamp",
          direction = Direction.DESC) Pageable pageable) {
    return repository.findAll(spec, pageable);
  }
}
