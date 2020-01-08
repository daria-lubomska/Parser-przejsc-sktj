package com.sktj.repository;

import com.sktj.entity.Cave;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CaveRepository extends CrudRepository<Cave, Long>, JpaSpecificationExecutor<Cave>,
    CaveRepositoryCustom {
}
