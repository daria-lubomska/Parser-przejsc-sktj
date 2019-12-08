package sktj.parser.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import sktj.parser.entity.Country;

@Repository
public interface CountryRepositoryCustom {
  Country findCountryByName(String name);
  List<Country> findCountryForLiveSearch(String someChars);
}
