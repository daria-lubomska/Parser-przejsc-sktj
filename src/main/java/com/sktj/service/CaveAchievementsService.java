package com.sktj.service;

import com.sktj.controller.specification.CaveAchievFiltersSpecification;
import com.sktj.controller.specification.CaveAchievSearchSpecification;
import com.sktj.dao.CaveAchievementsDao;
import com.sktj.entity.CaveAchievements;
import com.sktj.model.CaveAchievModel;
import com.sktj.repository.CaveAchievementsRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CaveAchievementsService {

  private CaveAchievementsDao dao;
  private final CaveAchievementsRepository repository;
  private final SaveUpdateProcessor processor;
  private final Mapper mapper;

  public CaveAchievementsService(CaveAchievementsDao dao,
      CaveAchievementsRepository repository, SaveUpdateProcessor processor,
      Mapper mapper) {
    this.dao = dao;
    this.repository = repository;
    this.processor = processor;
    this.mapper = mapper;
  }

  public List<CaveAchievModel> getAll() {
    List<CaveAchievModel> model = new ArrayList<>();
    repository.findAll().forEach(i -> model.add(mapper.mapCaveAchiev(i)));
    return model;
  }

  public CaveAchievModel findById(Long id) {
    CaveAchievements achiev = repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Cave achievement Not Found"));
    return mapper.mapCaveAchiev(achiev);
  }

  public void save(CaveAchievements achiev) {
    processor.saveCaveAchievProcess(achiev);
    repository.save(achiev);
  }

  public void update(Long id, CaveAchievements caveAchiev) {
    CaveAchievements editedCaveAchiev = repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Cave Achievement Not Found"));
    processor.updateCaveAchievProcess(caveAchiev, editedCaveAchiev);
    repository.save(editedCaveAchiev);
  }

  public void delete(Long id) {
    CaveAchievements caveAchiev = repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Cave Achievement Not Found"));
    repository.delete(caveAchiev);
  }

  public List<CaveAchievModel> filter(CaveAchievFiltersSpecification spec,
      @PageableDefault(size = 20, sort = "entryTimestamp",
          direction = Direction.DESC) Pageable pageable) {
    List<CaveAchievModel> model = new ArrayList<>();
    repository.findAll(spec, pageable).forEach(i -> model.add(mapper.mapCaveAchiev(i)));
    return model;
  }

  public List<CaveAchievModel> search(CaveAchievSearchSpecification spec,
      @PageableDefault(size = 20, sort = "entryTimestamp",
          direction = Direction.DESC) Pageable pageable) {
    List<CaveAchievModel> model = new ArrayList<>();
    repository.findAll(spec, pageable).forEach(i -> model.add(mapper.mapCaveAchiev(i)));
    return model;
  }

  //TODO check after Ghost integration

  public List<CaveAchievModel> getUserCaveAchievementsAndNotifications(
      String someGhostIntegration) {
    List<CaveAchievModel> model = new ArrayList<>();
    dao.findUsersCaveAchievs(someGhostIntegration)
        .forEach(i -> model.add(mapper.mapCaveAchiev(i)));
    return model;
  }
}
