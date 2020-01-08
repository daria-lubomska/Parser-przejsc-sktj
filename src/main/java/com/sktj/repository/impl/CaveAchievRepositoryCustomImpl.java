package com.sktj.repository.impl;

import com.sktj.entity.CaveAchievements;
import com.sktj.repository.CaveAchievRepositoryCustom;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class CaveAchievRepositoryCustomImpl implements CaveAchievRepositoryCustom {

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public List<CaveAchievements> findUsersCaveAchievs(String email) {
    Query query = entityManager
        .createNamedQuery("CaveAchievements.findUsersCaveAchievs")
        .setParameter("email", email);
    return query.getResultList();
  }
}
