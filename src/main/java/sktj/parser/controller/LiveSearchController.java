package sktj.parser.controller;

import java.util.List;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sktj.parser.controller.specification.CaveSpecification;
import sktj.parser.controller.specification.UserSpecification;
import sktj.parser.model.Cave;
import sktj.parser.model.User;
import sktj.parser.repository.CaveRepository;
import sktj.parser.repository.UserRepository;

@RestController
@RequestMapping("/livesearch")
public class LiveSearchController {

  private final CaveRepository caveRepository;
  private final UserRepository userRepository;
  private final Mapper mapper;

  @Autowired
  public LiveSearchController(CaveRepository caveRepository,
      UserRepository userRepository, Mapper mapper) {
    this.caveRepository = caveRepository;
    this.userRepository = userRepository;
    this.mapper = mapper;
  }

  @GetMapping("/caves")
  public List<Cave> getCaveForLiveSearch(CaveSpecification caveSpecification){
    return mapper.mapCaveEntityToModel(caveRepository.findAll(caveSpecification));
  }

  @GetMapping("/users")
  public List<User> getUsersForLiveSearch(UserSpecification userSpecification){
    return mapper.mapUserEntityToModel(userRepository.findAll(userSpecification));
  }
}
