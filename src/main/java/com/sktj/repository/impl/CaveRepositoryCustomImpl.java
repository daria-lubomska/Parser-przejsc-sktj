package com.sktj.repository.impl;

import com.sktj.entity.Cave;
import com.sktj.repository.CaveRepositoryCustom;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class CaveRepositoryCustomImpl implements CaveRepositoryCustom {

  @PersistenceContext
  EntityManager entityManager;

  public Cave findByNameAndRegion(String name, String region) {
    Query query = entityManager.createNamedQuery("Cave.findByNameAndRegion")
        .setParameter("name", name)
        .setParameter("region", region);
    return (Cave) query.getResultList().stream().findFirst().orElse(null);
  }
}
