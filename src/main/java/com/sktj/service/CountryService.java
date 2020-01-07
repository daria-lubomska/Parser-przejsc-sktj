package com.sktj.service;

import com.sktj.controller.specification.CountrySpecification;
import com.sktj.dao.CountryDao;
import com.sktj.entity.Country;
import com.sktj.model.CountryModel;
import com.sktj.repository.CountryRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

  private final CountryDao dao;
  private final CountryRepository repository;
  private final Mapper mapper;

  @Autowired
  public CountryService(CountryDao dao, CountryRepository repository, Mapper mapper) {
    this.dao = dao;
    this.repository = repository;
    this.mapper = mapper;
  }

  public List<CountryModel> searchCountry(CountrySpecification countrySpecification){
    List<CountryModel> model = new ArrayList<>();
    repository.findAll(countrySpecification).forEach(i->model.add(mapper.mapCountry(i)));
    return model;
  }

  Country findCountryByName(String name) {
    return dao.findCountryByName(name);
  }
}
