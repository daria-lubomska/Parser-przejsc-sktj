package sktj.parser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sktj.parser.controller.specification.CaveAchievSpecification;
import sktj.parser.entity.CaveAchievements;
import sktj.parser.repository.CaveAchievementsRepository;


@RestController
@RequestMapping("/caves")
public class CaveAchievsController {

  private final CaveAchievementsRepository caveAchievementsRepository;

  @Autowired
  public CaveAchievsController(
      CaveAchievementsRepository caveAchievementsRepository) {
    this.caveAchievementsRepository = caveAchievementsRepository;
  }

  @GetMapping(value = "/filter")
  public Iterable<CaveAchievements> getCaveAchiev(CaveAchievSpecification spec,
      @PageableDefault(size = 20, sort = "entryTimestamp",
          direction = Direction.DESC) Pageable pageable) {
    return caveAchievementsRepository.findAll(spec,pageable);
  }

}
