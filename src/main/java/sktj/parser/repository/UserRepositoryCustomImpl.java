package sktj.parser.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sktj.parser.entity.User;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public User findByEmail(String email) {
    Query query = entityManager
        .createQuery("select u from User u where u.email like :email", User.class)
        .setParameter("email", email);
    return (User) query.getResultList()
        .stream().findFirst().orElse(null);
  }
}
