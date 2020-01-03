package com.sktj.dao;

import com.sktj.entity.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

  @PersistenceContext
  EntityManager entityManager;

  public User findUserByEmail(String email) {
    Query query = entityManager.createNamedQuery("User.findUserByEmail")
        .setParameter("email", email);
    return (User)  query.getResultList().stream().findFirst().orElse(null);
  }
}
