package com.sktj.repository;

import com.sktj.entity.Country;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Long>,
    JpaSpecificationExecutor<Country> {
}
