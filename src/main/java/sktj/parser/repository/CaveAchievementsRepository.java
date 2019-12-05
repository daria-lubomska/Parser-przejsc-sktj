package sktj.parser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sktj.parser.entity.CaveAchievements;

@Repository
public interface CaveAchievementsRepository extends JpaRepository<CaveAchievements, Long> {

}
