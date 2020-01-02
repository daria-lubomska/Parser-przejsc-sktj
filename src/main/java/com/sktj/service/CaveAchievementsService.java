package com.sktj.service;

import com.sktj.dao.CaveAchievementsDao;
import com.sktj.entity.CaveAchievements;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CaveAchievementsService {

  private CaveAchievementsDao dao;

  @Autowired
  public CaveAchievementsService(CaveAchievementsDao dao) {
    this.dao = dao;
  }

  public Optional<CaveAchievements> find(Long id) {
    return dao.find(id);
  }

  public List<CaveAchievements> getAll() {
    return dao.findAll();
  }

  public List<CaveAchievements> findUsersCaveAchievs(String someGhostIntegration) {
    return dao.findUsersCaveAchievs(someGhostIntegration);
  }
}
