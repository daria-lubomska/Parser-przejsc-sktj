package com.sktj.service;

import com.sktj.dao.ClimbingAchievementsDao;
import com.sktj.entity.ClimbingAchievements;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClimbingService {

  private ClimbingAchievementsDao dao;

  @Autowired
  public ClimbingService(ClimbingAchievementsDao dao) {
    this.dao = dao;
  }

  public List<ClimbingAchievements> findUsersClimbingAchievs(String email) {
    return dao.findUsersClimbingAchievs(email);
  }
}
