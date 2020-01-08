package com.sktj.repository;

import com.sktj.entity.Cave;

public interface CaveRepositoryCustom {
  Cave findByNameAndRegion(String name, String region);
}
