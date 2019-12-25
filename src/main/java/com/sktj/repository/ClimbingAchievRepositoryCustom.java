package com.sktj.repository;

import com.sktj.entity.ClimbingAchievements;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ClimbingAchievRepositoryCustom {
  List<ClimbingAchievements> findUsersClimbingAchievs(String email);
}
