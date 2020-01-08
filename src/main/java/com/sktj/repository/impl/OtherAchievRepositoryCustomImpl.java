package com.sktj.repository.impl;

import com.sktj.entity.OtherActivityAchievements;
import com.sktj.repository.OtherAchievRepositoryCustom;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class OtherAchievRepositoryCustomImpl implements OtherAchievRepositoryCustom {

  @PersistenceContext
  EntityManager entityManager;

  public List<OtherActivityAchievements> findUsersOtherAchievs(String email) {
    Query query = entityManager
        .createNamedQuery("ClimbingAchievements.findUsersClimbingAchievs")
        .setParameter("email", email);
    return query.getResultList();
  }
}
