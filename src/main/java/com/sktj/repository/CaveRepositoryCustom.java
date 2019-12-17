package com.sktj.repository;

import org.springframework.stereotype.Repository;
import com.sktj.entity.Cave;

@Repository
public interface CaveRepositoryCustom {

  Cave findByNameAndRegion(String name, String region);
}
