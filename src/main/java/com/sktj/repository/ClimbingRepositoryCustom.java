package com.sktj.repository;

import com.sktj.entity.ClimbingAchievements;
import java.util.List;

public interface ClimbingRepositoryCustom {
  List<ClimbingAchievements> findUsersClimbingAchievs(String email);
}
