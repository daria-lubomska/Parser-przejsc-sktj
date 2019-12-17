package com.sktj.repository;

import org.springframework.stereotype.Repository;
import com.sktj.entity.Country;

@Repository
public interface CountryRepositoryCustom {
  Country findCountryByName(String name);
}
