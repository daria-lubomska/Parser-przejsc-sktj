package com.sktj.service;

import com.sktj.controller.specification.CaveSpecification;
import com.sktj.entity.Cave;
import com.sktj.model.CaveModel;
import com.sktj.repository.CaveRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaveService {

  private final CaveRepository repository;
  private final Mapper mapper;

  @Autowired
  public CaveService(CaveRepository repository, Mapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public List<CaveModel> searchCaves(CaveSpecification caveSpecification) {
    List<CaveModel> model = new ArrayList<>();
    repository.findAll(caveSpecification).forEach(i -> model.add(mapper.mapCave(i)));
    return model;
  }

  Cave findByNameAndRegion(String name, String region) {
    return repository.findByNameAndRegion(name, region);
  }
}
