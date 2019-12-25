package com.sktj.repository;

import com.sktj.entity.CaveAchievements;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CaveAchievRepositoryCustom {
  List<CaveAchievements> findUsersCaveAchievs(String email);
}
