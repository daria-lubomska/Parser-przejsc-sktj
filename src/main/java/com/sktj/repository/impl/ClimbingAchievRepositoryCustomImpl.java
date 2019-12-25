package com.sktj.repository.impl;

import com.sktj.entity.ClimbingAchievements;
import com.sktj.repository.ClimbingAchievRepositoryCustom;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;

public class ClimbingAchievRepositoryCustomImpl implements ClimbingAchievRepositoryCustom {

  @PersistenceContext
  EntityManager entityManager;

  @Transactional
  @Override
  public List<ClimbingAchievements> findUsersClimbingAchievs(String email) {
    Query query = entityManager
        .createNamedQuery("ClimbingAchievements.findUsersClimbingAchievs")
        .setParameter("email", email);
    return query.getResultList();
  }
}
