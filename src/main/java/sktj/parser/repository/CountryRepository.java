package sktj.parser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sktj.parser.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Long>, CountryRepositoryCustom {

}
