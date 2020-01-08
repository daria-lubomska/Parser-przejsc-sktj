package com.sktj.repository.impl;

import com.sktj.entity.Country;
import com.sktj.repository.CountryRepositoryCustom;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class CountryRepositoryImpl implements CountryRepositoryCustom {

  @PersistenceContext
  EntityManager entityManager;

  public Country findCountryByName(String name) {
    Query query = entityManager
        .createNamedQuery("Country.findCountryByName")
        .setParameter("name", name);
    return (Country)  query.getResultList().stream().findFirst().orElse(null);
  }
}
