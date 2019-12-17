package com.sktj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.sktj.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Long>,
    JpaSpecificationExecutor<Country>, CountryRepositoryCustom{

}
