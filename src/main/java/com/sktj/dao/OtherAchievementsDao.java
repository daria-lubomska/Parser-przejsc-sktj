package com.sktj.dao;

import com.sktj.entity.OtherActivityAchievements;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Component;

@Component
public class OtherAchievementsDao {

  @PersistenceContext
  EntityManager entityManager;

  public List<OtherActivityAchievements> findUsersOtherAchievs(String email) {
    Query query = entityManager
        .createNamedQuery("ClimbingAchievements.findUsersClimbingAchievs")
        .setParameter("email", email);
    return query.getResultList();
  }
}
