package com.sktj.service;

import com.sktj.controller.specification.ClimbingAchievFiltersSpecification;
import com.sktj.controller.specification.ClimbingAchievSearchSpecification;
import com.sktj.dao.ClimbingAchievementsDao;
import com.sktj.entity.ClimbingAchievements;
import com.sktj.model.ClimbingModel;
import com.sktj.repository.ClimbingRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ClimbingService {

  private ClimbingAchievementsDao dao;
  private final ClimbingRepository repository;
  private final SaveUpdateProcessor processor;
  private final Mapper mapper;

  @Autowired
  public ClimbingService(ClimbingAchievementsDao dao,
      ClimbingRepository repository, SaveUpdateProcessor processor, Mapper mapper) {
    this.dao = dao;
    this.repository = repository;
    this.processor = processor;
    this.mapper = mapper;
  }

  public List<ClimbingModel> getAll() {
    List<ClimbingModel> model = new ArrayList<>();
    repository.findAll().forEach(i -> model.add(mapper.mapClimbing(i)));
    return model;
  }

  public ClimbingModel get(Long id) {
    ClimbingAchievements achiev = repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Climbing Not Found"));
    return mapper.mapClimbing(achiev);
  }

  public void save(ClimbingAchievements achiev) {
    processor.saveProcess(achiev);
    repository.save(achiev);
  }

  public void update(Long id, ClimbingAchievements achiev) {
    ClimbingAchievements editedClimbingAcheiv = repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Climbing Achievement Not Found"));
    processor.updateClimbingAchievProcess(achiev, editedClimbingAcheiv);
    repository.save(editedClimbingAcheiv);
  }

  public void delete(Long id) {
    ClimbingAchievements achiev = repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Climbing Achievement Not Found"));
    repository.delete(achiev);
  }

  public List<ClimbingModel> filter(ClimbingAchievFiltersSpecification spec,
      @PageableDefault(size = 20, sort = "date",
          direction = Direction.DESC) Pageable pageable) {
    List<ClimbingModel> model = new ArrayList<>();
    repository.findAll(spec, pageable).forEach(i -> model.add(mapper.mapClimbing(i)));
    return model;
  }

  public List<ClimbingModel> search(ClimbingAchievSearchSpecification spec,
      @PageableDefault(size = 20, sort = "entryTimestamp",
          direction = Direction.DESC) Pageable pageable) {
    List<ClimbingModel> model = new ArrayList<>();
    repository.findAll(spec, pageable).forEach(i -> model.add(mapper.mapClimbing(i)));
    return model;
  }

  //TODO check after Ghost integration

  public List<ClimbingModel> getUserClimbingAchievementsAndNotifications(
      String someGhostIntegration) {
    List<ClimbingModel> model = new ArrayList<>();
    dao.findUsersClimbingAchievs(someGhostIntegration)
        .forEach(i -> model.add(mapper.mapClimbing(i)));
    return model;
  }
}
