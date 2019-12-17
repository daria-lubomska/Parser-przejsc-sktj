package com.sktj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.sktj.entity.CaveAchievements;

public interface CaveAchievementsRepository extends JpaRepository<CaveAchievements, Long> ,
    JpaSpecificationExecutor<CaveAchievements> {

}
