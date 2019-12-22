package com.sktj.controller;

import com.sktj.controller.specification.CaveAchievFiltersSpecification;
import com.sktj.controller.specification.CaveAchievSearchSpecification;
import com.sktj.entity.CaveAchievements;
import com.sktj.exception.ResourceNotFoundExeption;
import com.sktj.repository.CaveAchievementsRepository;
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
@RequestMapping(Mappings.CAVES)
public class CaveAchievsController {

  private final CaveAchievementsRepository caveAchievementsRepository;

  @Autowired
  public CaveAchievsController(
      CaveAchievementsRepository caveAchievementsRepository) {
    this.caveAchievementsRepository = caveAchievementsRepository;
  }

  @PostMapping(Mappings.ADD_CAVE_ACHIEV)
  public ResponseEntity<?> addNewCaveAchiev(@Valid @RequestBody CaveAchievements caveAchievement) {
    caveAchievementsRepository.save(caveAchievement);
    log.info("Cave achievement with notification time {}, created by {} added to DB successfully",
        caveAchievement.getNotificationTimestamp(), caveAchievement.getNotificationAuthor().getEmail());
    return new ResponseEntity<CaveAchievements>(HttpStatus.CREATED);
  }

  @PutMapping(Mappings.EDIT_CAVE_ACHIEV)
  public ResponseEntity<CaveAchievements> updateCaveAchiev(@PathVariable("caveAchievId")
      Long caveAchievId, @Valid @RequestBody CaveAchievements caveAchiev)
      throws ResourceNotFoundExeption {
    CaveAchievements caveAchievEdited = caveAchievementsRepository.findById(caveAchievId)
        .orElseThrow(() -> new ResourceNotFoundExeption("Cave achievement with id "
            + caveAchievId +" does not exist!" + HttpStatus.NOT_FOUND));
    caveAchievementsRepository.delete(caveAchievEdited);
    caveAchievEdited.setNotificationTimestamp(caveAchiev.getNotificationTimestamp());
    caveAchievEdited.setCaveName(caveAchiev.getCaveName());
    caveAchievEdited.setAuthorsFromAnotherClubs(caveAchiev.getAuthorsFromAnotherClubs());
    caveAchievEdited.setAuthors(caveAchiev.getAuthors());
    caveAchievEdited.setCaveOvercomeStyle(caveAchiev.getCaveOvercomeStyle());
    caveAchievEdited.setComment(caveAchiev.getComment());
    caveAchievEdited.setCountry(caveAchiev.getCountry());
    caveAchievEdited.setEntryTimestamp(caveAchiev.getEntryTimestamp());
    caveAchievEdited.setExitTimestamp(caveAchiev.getExitTimestamp());
    caveAchievEdited.setReachedParts(caveAchiev.getReachedParts());
    caveAchievEdited.setNotificationAuthor(caveAchiev.getNotificationAuthor());
    caveAchievementsRepository.save(caveAchievEdited);
    log.info("Cave achievement with id {} updated successfully", caveAchievId);
    return ResponseEntity.ok(caveAchievEdited);
  }

  @DeleteMapping(Mappings.DELETE_CAVE_ACHIEV)
  public ResponseEntity<?> deleteCaveAchiev(@PathVariable("caveAchievId") Long caveAchievId)
      throws ResourceNotFoundExeption {
    CaveAchievements caveAchiev = caveAchievementsRepository.findById(caveAchievId)
        .orElseThrow(() -> new ResourceNotFoundExeption
            ("Cave achievement with id "+ caveAchievId +" does not exist!" + HttpStatus.NOT_FOUND));
    caveAchievementsRepository.delete(caveAchiev);
    log.info("Cave achievement with id {} removed successfully",caveAchievId);
    return new ResponseEntity<CaveAchievements>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(Mappings.FILTER)
  public Iterable<CaveAchievements> getCaveAchievByFilters(CaveAchievFiltersSpecification spec,
      @PageableDefault(size = 20, sort = "entryTimestamp",
          direction = Direction.DESC) Pageable pageable) {
    return caveAchievementsRepository.findAll(spec,pageable);
  }

  @GetMapping(Mappings.SEARCH)
  public Iterable<CaveAchievements> getCaveAchievBySearch(CaveAchievSearchSpecification spec,
      @PageableDefault(size = 20, sort = "entryTimestamp",
          direction = Direction.DESC) Pageable pageable) {
    return caveAchievementsRepository.findAll(spec,pageable);
  }
}
