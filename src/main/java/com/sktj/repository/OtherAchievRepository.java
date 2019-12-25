package com.sktj.repository;

import com.sktj.entity.OtherActivityAchievements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OtherAchievRepository extends JpaRepository<OtherActivityAchievements, Long>,
    JpaSpecificationExecutor<OtherActivityAchievements>, OtherAchievRepositoryCustom {

}
