package com.sktj.repository;

import com.sktj.entity.ClimbingAchievements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClimbingRepository extends JpaRepository<ClimbingAchievements, Long>,
    JpaSpecificationExecutor<ClimbingAchievements>, ClimbingAchievRepositoryCustom {
}
