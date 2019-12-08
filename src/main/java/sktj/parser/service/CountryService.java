package sktj.parser.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sktj.parser.entity.Country;
import sktj.parser.repository.CountryRepository;

@Service
public class CountryService {

  private final CountryRepository countryRepository;

  @Autowired
  public CountryService(CountryRepository countryRepository) {
    this.countryRepository = countryRepository;
  }

  public List<String> findCountryForLiveSearch(String someChars) {
    List<Country> countries = countryRepository.findCountryForLiveSearch(someChars);
    return countries.stream().map(Country::getName).collect(Collectors.toList());
  }
}
