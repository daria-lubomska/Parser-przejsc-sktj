package com.sktj.repository.impl;

import com.sktj.entity.Cave;
import com.sktj.repository.CaveRepositoryCustom;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;

public class CaveRepositoryCustomImpl implements CaveRepositoryCustom {

  @PersistenceContext
  EntityManager entityManager;

  @Transactional
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
