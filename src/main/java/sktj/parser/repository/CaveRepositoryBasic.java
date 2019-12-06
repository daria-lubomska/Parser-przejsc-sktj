package sktj.parser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sktj.parser.entity.Cave;

public interface CaveRepositoryBasic extends JpaRepository<Cave, Long> {
}
