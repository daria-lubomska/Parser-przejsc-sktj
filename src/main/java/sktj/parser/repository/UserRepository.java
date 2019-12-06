package sktj.parser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sktj.parser.entity.User;

public interface UserRepository extends JpaRepository<User,Long>, UserRepositoryCustom {
}
