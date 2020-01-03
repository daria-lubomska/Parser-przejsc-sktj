package com.sktj.service;

import com.sktj.dao.OtherAchievementsDao;
import com.sktj.entity.OtherActivityAchievements;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtherService {

  private OtherAchievementsDao dao;

  @Autowired
  public OtherService(OtherAchievementsDao dao) {
    this.dao = dao;
  }

  public List<OtherActivityAchievements> findUsersOtherAchievs(String email) {
    return dao.findUsersOtherAchievs(email);
  }
}
