package com.sktj.controller;

import com.sktj.controller.specification.CaveSpecification;
import com.sktj.controller.specification.CountrySpecification;
import com.sktj.controller.specification.UserSpecification;
import com.sktj.mapper.Mapper;
import com.sktj.model.Cave;
import com.sktj.model.Country;
import com.sktj.model.User;
import com.sktj.repository.CaveRepository;
import com.sktj.repository.CountryRepository;
import com.sktj.repository.UserRepository;
import com.sktj.util.Mappings;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Mappings.LIVE)
public class LiveSearchController {

  private final CaveRepository caveRepository;
  private final UserRepository userRepository;
  private final CountryRepository countryRepository;
  private final Mapper mapper;

  @Autowired
  public LiveSearchController(CaveRepository caveRepository,
      UserRepository userRepository, CountryRepository countryRepository,
      Mapper mapper) {
    this.caveRepository = caveRepository;
    this.userRepository = userRepository;
    this.countryRepository = countryRepository;
    this.mapper = mapper;
  }

  @GetMapping(Mappings.CAVES)
  public List<Cave> getCaveForLiveSearch(CaveSpecification caveSpecification){
    return mapper.mapCaveEntityToModel(caveRepository.findAll(caveSpecification));
  }

  @GetMapping(Mappings.LIVE_USERS)
  public List<User> getUsersForLiveSearch(UserSpecification userSpecification){
    return mapper.mapUserEntityToModel(userRepository.findAll(userSpecification));
  }

  @GetMapping(Mappings.LIVE_COUNTRIES)
  public List<Country> getCountriesForLiveSearch(CountrySpecification countrySpecification){
    return mapper.mapCountryEntityToModel(countryRepository.findAll(countrySpecification));
  }
}
