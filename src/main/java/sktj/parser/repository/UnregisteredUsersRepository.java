package sktj.parser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sktj.parser.entity.UsersUnregistered;

public interface UnregisteredUsersRepository extends JpaRepository<UsersUnregistered, Long> {

}
