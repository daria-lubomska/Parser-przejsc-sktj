package com.sktj.repository.impl;

import com.sktj.entity.ClimbingAchievements;
import com.sktj.repository.ClimbingRepositoryCustom;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class ClimbingAchievRepositoryCustomImpl implements ClimbingRepositoryCustom {

  @PersistenceContext
  EntityManager entityManager;

  public List<ClimbingAchievements> findUsersClimbingAchievs(String email) {
    Query query = entityManager
        .createNamedQuery("ClimbingAchievements.findUsersClimbingAchievs")
        .setParameter("email", email);
    return query.getResultList();
  }
}
