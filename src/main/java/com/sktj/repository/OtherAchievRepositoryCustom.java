package com.sktj.repository;

import com.sktj.entity.OtherActivityAchievements;
import java.util.List;

public interface OtherAchievRepositoryCustom {
  List<OtherActivityAchievements> findUsersOtherAchievs(String email);
}
