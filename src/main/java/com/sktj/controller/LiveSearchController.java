package com.sktj.controller;

import com.sktj.controller.specification.CaveSpecification;
import com.sktj.controller.specification.CountrySpecification;
import com.sktj.controller.specification.UserSpecification;
import com.sktj.service.Mapper;
import com.sktj.model.CaveModel;
import com.sktj.model.CountryModel;
import com.sktj.model.UserModel;
import com.sktj.repository.CaveRepository;
import com.sktj.repository.CountryRepository;
import com.sktj.repository.UserRepository;
import com.sktj.util.Mappings;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Mappings.LIVE)
public class LiveSearchController {

  private final CaveRepository caveService;
  private final UserRepository userRepository;
  private final CountryRepository countryRepository;
  private final Mapper mapper;

  @Autowired
  public LiveSearchController(CaveRepository caveService,
      UserRepository userRepository, CountryRepository countryRepository,
      Mapper mapper) {
    this.caveService = caveService;
    this.userRepository = userRepository;
    this.countryRepository = countryRepository;
    this.mapper = mapper;
  }

  @GetMapping(Mappings.CAVES)
  public List<CaveModel> searchCaves(CaveSpecification caveSpecification){
    List<CaveModel> model = new ArrayList<>();
    caveService.findAll(caveSpecification).forEach(i->model.add(mapper.mapCave(i)));
    return model;
  }

  @GetMapping(Mappings.USERS)
  public List<UserModel> searchUsers(UserSpecification userSpecification){
    List<UserModel> model = new ArrayList<>();
    userRepository.findAll(userSpecification).forEach(i-> model.add(mapper.mapUser(i)));
    return model;
  }

  @GetMapping(Mappings.COUNTRIES)
  public List<CountryModel> searchCountry(CountrySpecification countrySpecification){
    List<CountryModel> model = new ArrayList<>();
    countryRepository.findAll(countrySpecification).forEach(i->model.add(mapper.mapCountry(i)));
    return model;
  }
}
