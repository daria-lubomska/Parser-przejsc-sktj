package com.sktj.dao;

import com.sktj.entity.ClimbingAchievements;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Component;

@Component
public class ClimbingAchievementsDao {

  @PersistenceContext
  EntityManager entityManager;

  public List<ClimbingAchievements> findUsersClimbingAchievs(String email) {
    Query query = entityManager
        .createNamedQuery("ClimbingAchievements.findUsersClimbingAchievs")
        .setParameter("email", email);
    return query.getResultList();
  }
}
