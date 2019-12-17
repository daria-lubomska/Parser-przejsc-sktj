package com.sktj.repository.impl;

import com.sktj.repository.CaveRepositoryCustom;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.sktj.entity.Cave;

public class CaveRepositoryCustomImpl implements CaveRepositoryCustom {

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public Cave findByNameAndRegion(String name, String region) {
    Query query = entityManager
        .createQuery("select c from Cave c where c.name like :name and c.region like :region",
            Cave.class)
        .setParameter("name", name).setParameter("region", region);
    return (Cave) query.getResultList()
        .stream().findFirst().orElse(null);
  }
}
