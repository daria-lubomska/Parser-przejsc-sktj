package com.sktj.repository;

import com.sktj.entity.Country;

public interface CountryRepositoryCustom {
  Country findCountryByName(String name);
}
