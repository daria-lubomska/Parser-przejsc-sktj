package sktj.parser.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sktj.parser.entity.Country;
import sktj.parser.repository.CountryRepositoryCustom;

public class CountryRepositoryCustomImpl implements CountryRepositoryCustom {

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public Country findCountryByName(String name) {
    Query query = entityManager
        .createNamedQuery("Country.findCountryByName")
        .setParameter("name", name);
    return (Country) query.getResultList()
        .stream().findFirst().orElse(null);
  }
}
