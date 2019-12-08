package sktj.parser.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import sktj.parser.entity.Cave;

@Repository
public interface CaveRepositoryCustom {
  Cave findByNameAndRegion(String name, String region);
  List<Cave> findCaveForLiveSearch(String someChars);
}
