package com.sktj.repository;

import com.sktj.entity.CaveAchievements;
import java.util.List;

public interface CaveAchievRepositoryCustom {
  List<CaveAchievements> findUsersCaveAchievs(String email);
}
