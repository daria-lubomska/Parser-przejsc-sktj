package com.sktj.repository;

import com.sktj.entity.OtherActivityAchievements;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface OtherAchievRepository extends CrudRepository<OtherActivityAchievements, Long>,
    JpaSpecificationExecutor<OtherActivityAchievements> {
}
