package com.sktj.service;

import com.sktj.controller.specification.OtherAchievFiltersSpecification;
import com.sktj.controller.specification.OtherAchievSearchSpecification;
import com.sktj.entity.OtherActivityAchievements;
import com.sktj.model.OtherAchievModel;
import com.sktj.repository.OtherAchievRepository;
import com.sktj.util.Mappings;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OtherService {

  private final OtherAchievRepository repository;
  private final SaveUpdateProcessor processor;
  private final Mapper mapper;

  @Autowired
  public OtherService(OtherAchievRepository repository,
      SaveUpdateProcessor processor, Mapper mapper) {
    this.repository = repository;
    this.processor = processor;
    this.mapper = mapper;
  }

  public List<OtherAchievModel> getAll() {
    List<OtherAchievModel> model = new ArrayList<>();
    repository.findAll().forEach(i -> model.add(mapper.mapOtherAchiev(i)));
    return model;
  }

  public OtherAchievModel get(@PathVariable("otherId") Long id) {
    OtherActivityAchievements achiev = repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Other Activities Achievement Not Found"));
    return mapper.mapOtherAchiev(achiev);
  }

  public void save(@Valid @RequestBody OtherActivityAchievements achiev) {
    processor.saveProcess(achiev);
    repository.save(achiev);
  }

  public void update(Long id, OtherActivityAchievements achiev) {
    OtherActivityAchievements editedAchiev = repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Other Activities Achievement Not Found"));
    processor.updateOtherAchievProcess(achiev, editedAchiev);
    repository.save(editedAchiev);
  }

  public void delete(Long id) {
    OtherActivityAchievements editedAchiev = repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Other Activities Achievement Not Found"));
    repository.delete(editedAchiev);
  }

  public List<OtherAchievModel> filter(
      OtherAchievFiltersSpecification spec,
      @PageableDefault(size = 20, sort = "date",
          direction = Direction.DESC) Pageable pageable) {
    List<OtherAchievModel> model = new ArrayList<>();
    repository.findAll(spec, pageable).forEach(i -> model.add(mapper.mapOtherAchiev(i)));
    return model;
  }

  public List<OtherAchievModel> search(
      OtherAchievSearchSpecification spec,
      @PageableDefault(size = 20, sort = "date",
          direction = Direction.DESC) Pageable pageable) {
    List<OtherAchievModel> model = new ArrayList<>();
    repository.findAll(spec, pageable).forEach(i -> model.add(mapper.mapOtherAchiev(i)));
    return model;
  }

  //TODO check after Ghost integration

  @GetMapping(Mappings.OTHERS_AND_NOTIF)
  public List<OtherAchievModel> getUserOtherAchievementsAndNotifications(
      String someGhostIntegration) {
    List<OtherAchievModel> model = new ArrayList<>();
    repository.findUsersOtherAchievs(someGhostIntegration)
        .forEach(i -> model.add(mapper.mapOtherAchiev(i)));
    return model;
  }
}
