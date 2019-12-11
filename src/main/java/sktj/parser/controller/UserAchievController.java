package sktj.parser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sktj.parser.entity.CaveAchievements;
import sktj.parser.repository.CaveAchievementsRepository;

@RestController
@RequestMapping("/clientCaves")
public class UserAchievController {

  private final CaveAchievementsRepository caveRepository;

  @Autowired
  public UserAchievController(CaveAchievementsRepository caveRepository) {
    this.caveRepository = caveRepository;
  }

  //TODO integration with Ghost - HttpSession attributes (name, surname, email) od currently logged user?
  @GetMapping
  public Iterable<CaveAchievements> getAll(@PageableDefault(size = 20, sort = "entryTimestamp",
      direction = Direction.DESC) Pageable pageable) {
    return caveRepository.findAll(pageable);
  }

  @DeleteMapping("/{caveAchievId}")
  public String deleteCaveAchiev(@PathVariable("caveAchievId") Long caveAchievId) {
    CaveAchievements caveAchiev = caveRepository.findById(caveAchievId)
        .orElseThrow(() -> new IllegalArgumentException("This cave achievement does not exist!"));
    caveRepository.delete(caveAchiev);
    return "redirect:/clientCaves";
  }
}
