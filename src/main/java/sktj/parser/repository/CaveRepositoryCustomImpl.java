package sktj.parser.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sktj.parser.entity.Cave;

public class CaveRepositoryCustomImpl implements CaveRepositoryCustom {

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public Cave findByName(String name) {
    Query query = entityManager
        .createQuery("select c from Cave c where c.name like :name", Cave.class);
    return (Cave) query.getResultList().get(0);
  }
}
