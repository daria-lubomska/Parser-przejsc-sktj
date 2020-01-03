package com.sktj.dao;

import com.sktj.entity.Country;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Component;

@Component
public class CountryDao {

  @PersistenceContext
  EntityManager entityManager;

  public Country findCountryByName(String name) {
    Query query = entityManager
        .createNamedQuery("Country.findCountryByName")
        .setParameter("name", name);
    return (Country)  query.getResultList().stream().findFirst().orElse(null);
  }
}
