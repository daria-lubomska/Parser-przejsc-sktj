package com.sktj.repository;

import com.sktj.entity.OtherActivityAchievements;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherAchievRepositoryCustom {
  public List<OtherActivityAchievements> findUsersOtherAchievs(String email);
}
