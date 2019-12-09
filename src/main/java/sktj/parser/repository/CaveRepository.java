package sktj.parser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sktj.parser.entity.Cave;

public interface CaveRepository extends JpaRepository<Cave, Long>,
    JpaSpecificationExecutor<Cave>, CaveRepositoryCustom{
}
