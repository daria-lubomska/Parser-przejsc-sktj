package sktj.parser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sktj.parser.entity.Cave;

@Repository
public interface CaveRepositoryBasic extends JpaRepository<Cave, Long> {
}
