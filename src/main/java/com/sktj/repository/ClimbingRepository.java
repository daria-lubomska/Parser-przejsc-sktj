package com.sktj.repository;

import com.sktj.entity.ClimbingAchievements;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ClimbingRepository extends CrudRepository<ClimbingAchievements, Long>,
    JpaSpecificationExecutor<ClimbingAchievements> {
}
