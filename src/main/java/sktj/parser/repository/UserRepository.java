package sktj.parser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sktj.parser.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

}
