package com.sktj.service;

import com.sktj.dao.CaveAchievementsDao;
import com.sktj.entity.CaveAchievements;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaveAchievementsService {

  private CaveAchievementsDao dao;

  @Autowired
  public CaveAchievementsService(CaveAchievementsDao dao) {
    this.dao = dao;
  }

  public List<CaveAchievements> findUsersCaveAchievs(String someGhostIntegration) {
    return dao.findUsersCaveAchievs(someGhostIntegration);
  }
}
