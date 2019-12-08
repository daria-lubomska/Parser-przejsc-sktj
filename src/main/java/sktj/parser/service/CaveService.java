package sktj.parser.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sktj.parser.entity.Cave;
import sktj.parser.repository.CaveRepository;

@Service
public class CaveService {

  private final CaveRepository caveRepository;

  @Autowired
  public CaveService(CaveRepository caveRepository) {
    this.caveRepository = caveRepository;
  }

  public List<Cave> findCaveForLiveSearch(String someChars) {
    return caveRepository.findCaveForLiveSearch(someChars);
  }
}
