package com.sktj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.sktj.entity.User;

public interface UserRepository extends JpaRepository<User, Long>,
    JpaSpecificationExecutor<User>, UserRepositoryCustom {
}
