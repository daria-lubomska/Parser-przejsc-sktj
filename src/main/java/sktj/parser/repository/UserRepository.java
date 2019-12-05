package sktj.parser.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends UserRepositoryBasic, UserRepositoryCustom {
}
