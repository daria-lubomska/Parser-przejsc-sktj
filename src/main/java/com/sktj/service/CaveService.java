package com.sktj.service;

import com.sktj.dao.CaveDao;
import com.sktj.entity.Cave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaveService {

  private CaveDao dao;

  @Autowired
  public CaveService(CaveDao dao) {
    this.dao = dao;
  }

  public Cave findByNameAndRegion(String name, String region) {
    return dao.findByNameAndRegion(name,region);
  }
}
