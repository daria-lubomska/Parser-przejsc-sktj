package sktj.parser.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sktj.parser.entity.User;
import sktj.parser.repository.UserRepositoryCustom;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public User findUserByEmail(String email) {
    Query query = entityManager.createNamedQuery("User.findUserByEmail")
        .setParameter("email", email);
    return (User) query.getResultList().stream().findFirst().orElse(null);
  }

  @Override
  public User findUserByNameAndSurname(String name, String surname) { //TODO co jak imię i nazwisko nie bedzie unikalne?
    Query query = entityManager.createNamedQuery("User.findByNameAndSurname")
        .setParameter("name", name)
        .setParameter("surname",surname);
    return (User) query.getResultList()
        .stream().findFirst().orElse(null);
  }
}
