package com.sktj.controller;

import com.sktj.controller.specification.OtherAchievFiltersSpecification;
import com.sktj.controller.specification.OtherAchievSearchSpecification;
import com.sktj.entity.OtherActivityAchievements;
import com.sktj.exception.ResourceNotFoundExeption;
import com.sktj.repository.OtherAchievementsRepository;
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
@RequestMapping(Mappings.OTHERS)
public class OtherAchievsController {

  private final OtherAchievementsRepository repository;

  @Autowired
  public OtherAchievsController(OtherAchievementsRepository repository) {
    this.repository = repository;
  }

  @PostMapping(Mappings.ADD_NEW)
  public ResponseEntity<?> save(@Valid @RequestBody OtherActivityAchievements achiev) {
    repository.save(achiev);
    log.info(
        "Other activity achievement with notification time {}, created by {} saved successfully",
        achiev.getNotificationTimestamp(),
        achiev.getNotificationAuthor().getEmail());
    return new ResponseEntity<OtherActivityAchievements>(HttpStatus.CREATED);
  }

  @PutMapping(Mappings.EDIT_OTHER_ACHIEV)
  public ResponseEntity<OtherActivityAchievements> update(@PathVariable("otherId")
      Long otherId, @Valid @RequestBody OtherActivityAchievements achiev)
      throws ResourceNotFoundExeption {
    OtherActivityAchievements editedAchiev = repository.findById(otherId)
        .orElseThrow(() -> new ResourceNotFoundExeption("Other activity achievement with id "
            + otherId + " does not exist!" + HttpStatus.NOT_FOUND));
    repository.delete(editedAchiev);
    editedAchiev.setAnotherAuthors(achiev.getAnotherAuthors());
    editedAchiev.setAuthors(achiev.getAuthors());
    editedAchiev.setCategory(achiev.getCategory());
    editedAchiev.setComment(achiev.getComment());
    editedAchiev.setNotificationAuthor(achiev.getNotificationAuthor());
    editedAchiev.setNotificationTimestamp(achiev.getNotificationTimestamp());
    editedAchiev.setRegion(achiev.getRegion());
    editedAchiev.setDuration(achiev.getDuration());
    editedAchiev.setCountry(achiev.getCountry());
    editedAchiev.setDate(achiev.getDate());
    editedAchiev.setAchievementDescription(achiev.getAchievementDescription());
    repository.save(editedAchiev);
    log.info("Other activity achievement with id {} updated successfully", otherId);
    return ResponseEntity.ok(editedAchiev);
  }

  @DeleteMapping(Mappings.DELETE_OTHER_ACHIEV)
  public ResponseEntity<?> delete(@PathVariable("otherId") Long otherId)
      throws ResourceNotFoundExeption {
    OtherActivityAchievements editedAchiev = repository.findById(otherId)
        .orElseThrow(() -> new ResourceNotFoundExeption("Other activity achievement with id "
            + otherId + " does not exist!" + HttpStatus.NOT_FOUND));
    repository.delete(editedAchiev);
    log.info("Other activity achievement with id  {} removed successfully", otherId);
    return new ResponseEntity<OtherActivityAchievements>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(Mappings.FILTER)
  public Iterable<OtherActivityAchievements> filter(
      OtherAchievFiltersSpecification spec,
      @PageableDefault(size = 20, sort = "date",
          direction = Direction.DESC) Pageable pageable) {
    return repository.findAll(spec, pageable);
  }

  @GetMapping(Mappings.SEARCH)
  public Iterable<OtherActivityAchievements> search(
      OtherAchievSearchSpecification spec,
      @PageableDefault(size = 20, sort = "date",
          direction = Direction.DESC) Pageable pageable) {
    return repository.findAll(spec, pageable);
  }
}
