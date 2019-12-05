package sktj.parser.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sktj.parser.entity.Country;

public class CountryRepositoryCustomImpl implements CountryRepositoryCustom {

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public Country findByNameAndRegion(String name, String region) {
    Query query = entityManager
        .createQuery("select c from Country c where c.name like :name and c.region like :region", Country.class);
    return (Country) query.getResultList().get(0);
  }
}
