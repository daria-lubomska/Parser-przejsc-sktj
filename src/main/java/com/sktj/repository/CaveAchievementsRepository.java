package com.sktj.repository;

import com.sktj.entity.CaveAchievements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CaveAchievementsRepository extends JpaRepository<CaveAchievements, Long> ,
    JpaSpecificationExecutor<CaveAchievements>, CaveAchievRepositoryCustom {
}
