package com.sktj.dao;

import com.sktj.entity.CaveAchievements;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Component;

@Component
public class CaveAchievementsDao {

  @PersistenceContext
  EntityManager entityManager;

  public List<CaveAchievements> findUsersCaveAchievs(String email) {
    Query query = entityManager
        .createNamedQuery("CaveAchievements.findUsersCaveAchievs")
        .setParameter("email", email);
    return query.getResultList();
  }
}
