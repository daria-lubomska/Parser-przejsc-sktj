package sktj.parser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sktj.parser.entity.CaveAchievements;

public interface CaveAchievementsRepository extends JpaRepository<CaveAchievements, Long> ,
    JpaSpecificationExecutor<CaveAchievements> {

}
