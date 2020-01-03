package com.sktj.service;

import com.sktj.dao.CountryDao;
import com.sktj.entity.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

  private CountryDao dao;

  @Autowired
  public CountryService(CountryDao dao) {
    this.dao = dao;
  }

  public Country findCountryByName(String name) {
    return dao.findCountryByName(name);
  }
}
