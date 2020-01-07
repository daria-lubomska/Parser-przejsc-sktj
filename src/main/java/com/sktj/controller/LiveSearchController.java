package com.sktj.controller;

import com.sktj.controller.specification.CaveSpecification;
import com.sktj.controller.specification.CountrySpecification;
import com.sktj.controller.specification.UserSpecification;
import com.sktj.model.CaveModel;
import com.sktj.model.CountryModel;
import com.sktj.model.UserModel;
import com.sktj.service.CaveService;
import com.sktj.service.CountryService;
import com.sktj.service.UserService;
import com.sktj.util.Mappings;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Mappings.LIVE)
public class LiveSearchController {

  private final CaveService caveService;
  private final UserService userService;
  private final CountryService countryService;

  public LiveSearchController(CaveService caveService, UserService userService,
      CountryService countryService) {
    this.caveService = caveService;
    this.userService = userService;
    this.countryService = countryService;
  }

  @GetMapping(Mappings.CAVES)
  public List<CaveModel> searchCaves(CaveSpecification caveSpecification){
    return caveService.searchCaves(caveSpecification);
  }

  @GetMapping(Mappings.USERS)
  public List<UserModel> searchUsers(UserSpecification userSpecification){
    return userService.searchUsers(userSpecification);
  }

  @GetMapping(Mappings.COUNTRIES)
  public List<CountryModel> searchCountry(CountrySpecification countrySpecification){
    return countryService.searchCountry(countrySpecification);
  }
}
