package com.sktj.controller;

import com.sktj.controller.specification.ClimbingAchievFiltersSpecification;
import com.sktj.controller.specification.ClimbingAchievSearchSpecification;
import com.sktj.entity.CaveAchievements;
import com.sktj.entity.ClimbingAchievements;
import com.sktj.exception.ResourceNotFoundExeption;
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
  public ResponseEntity<?> addClimbingAchiev(@Valid @RequestBody ClimbingAchievements achiev) {
    repository.save(achiev);
    log.info("Climbing achievement with notification time {}, created by {} saved successfully",
        achiev.getNotificationTimestamp(), achiev.getNotificationAuthor().getEmail());
    return new ResponseEntity<CaveAchievements>(HttpStatus.CREATED);
  }

  @PutMapping(Mappings.EDIT_CLIMBING)
  public ResponseEntity<ClimbingAchievements> updateClimbingAchiev(@PathVariable("climbingId")
      Long climbingId, @Valid @RequestBody ClimbingAchievements achiev)
      throws ResourceNotFoundExeption {
    ClimbingAchievements achievToEdit = repository.findById(climbingId)
        .orElseThrow(() -> new ResourceNotFoundExeption("Climbing achievement with id "
            + climbingId + " does not exist!" + HttpStatus.NOT_FOUND));
    repository.delete(achievToEdit);
    achievToEdit.setDuration(achiev.getDuration());
    achievToEdit.setAnotherAuthors(achiev.getAnotherAuthors());
    achievToEdit.setCountry(achiev.getCountry());
    achievToEdit.setRegion(achiev.getRegion());
    achievToEdit.setWall(achiev.getWall());
    achievToEdit.setDifficultyGrade(achiev.getDifficultyGrade());
    achievToEdit.setRouteName(achiev.getRouteName());
    achievToEdit.setDate(achiev.getDate());
    achievToEdit.setNotificationTimestamp(achiev.getNotificationTimestamp());
    achievToEdit.setNotificationAuthor(achiev.getNotificationAuthor());
    achievToEdit.setComment(achiev.getComment());
    achievToEdit.setAuthors(achiev.getAuthors());
    repository.save(achievToEdit);
    log.info("Climbing achievement with id {} updated successfully", climbingId);
    return ResponseEntity.ok(achievToEdit);
  }

  @DeleteMapping(Mappings.DELETE_CLIMBING)
  public ResponseEntity<?> deleteClimbingAchiev(@PathVariable("climbingId") Long climbingId)
      throws ResourceNotFoundExeption {
    ClimbingAchievements achiev = repository.findById(climbingId)
        .orElseThrow(() -> new ResourceNotFoundExeption
            ("Climbing achievement with id " + climbingId + " does not exist!"
                + HttpStatus.NOT_FOUND));
    repository.delete(achiev);
    log.info("Climbing achievement with id {} removed successfully", climbingId);
    return new ResponseEntity<ClimbingAchievements>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(Mappings.FILTER)
  public Iterable<ClimbingAchievements> filterClimbingAchiev(
      ClimbingAchievFiltersSpecification spec,
      @PageableDefault(size = 20, sort = "date",
          direction = Direction.DESC) Pageable pageable) {
    return repository.findAll(spec, pageable);
  }

  @GetMapping(Mappings.SEARCH)
  public Iterable<ClimbingAchievements> searchClimbing(ClimbingAchievSearchSpecification spec,
      @PageableDefault(size = 20, sort = "entryTimestamp",
          direction = Direction.DESC) Pageable pageable) {
    return repository.findAll(spec, pageable);
  }
}
