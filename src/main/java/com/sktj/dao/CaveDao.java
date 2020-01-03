package com.sktj.dao;

import com.sktj.entity.Cave;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Component;

@Component
public class CaveDao {

  @PersistenceContext
  EntityManager entityManager;

  public Cave findByNameAndRegion(String name, String region) {
    Query query = entityManager.createNamedQuery("Cave.findByNameAndRegion")
        .setParameter("name", name)
        .setParameter("region", region);
    return (Cave) query.getResultList().stream().findFirst().orElse(null);
  }
}
