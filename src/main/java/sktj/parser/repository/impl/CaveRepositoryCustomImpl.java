package sktj.parser.repository.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sktj.parser.entity.Cave;
import sktj.parser.repository.CaveRepositoryCustom;

public class CaveRepositoryCustomImpl implements CaveRepositoryCustom {

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public Cave findByNameAndRegion(String name, String region) {
    Query query = entityManager
          .createQuery("select c from Cave c where c.name like :name and c.region like :region",
              Cave.class)
          .setParameter("name", name).setParameter("region", region);
    return (Cave) query.getResultList()
        .stream().findFirst().orElse(null);
  }

  @Override
  public List<Cave> findCaveForLiveSearch(String someChars) {
    Query query = entityManager
        .createNamedQuery("Cave.findCaveForLiveSearch")
        .setParameter("someChars", someChars);
    return query.getResultList();
  }
}
