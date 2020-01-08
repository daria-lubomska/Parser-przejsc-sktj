package com.sktj.repository.impl;

import com.sktj.entity.User;
import com.sktj.repository.UserRepositoryCustom;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {

  @PersistenceContext
  EntityManager entityManager;

  public User findUserByEmail(String email) {
    Query query = entityManager.createNamedQuery("User.findUserByEmail")
        .setParameter("email", email);
    return (User)  query.getResultList().stream().findFirst().orElse(null);
  }
}
