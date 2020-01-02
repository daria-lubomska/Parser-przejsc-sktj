package com.sktj.repository;

import com.sktj.entity.CaveAchievements;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CaveAchievementsRepository extends CrudRepository<CaveAchievements, Long>,
    JpaSpecificationExecutor<CaveAchievements> {
}
