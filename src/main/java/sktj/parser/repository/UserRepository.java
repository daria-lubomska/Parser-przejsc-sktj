package sktj.parser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sktj.parser.entity.User;

public interface UserRepository extends JpaRepository<User, Long>,
    JpaSpecificationExecutor<User>, UserRepositoryCustom {
}
