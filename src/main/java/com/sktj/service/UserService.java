package com.sktj.service;

import com.sktj.configuration.AppProperties;
import com.sktj.controller.specification.UserSpecification;
import com.sktj.dao.UserDao;
import com.sktj.entity.User;
import com.sktj.exception.ForbiddenActionExeption;
import com.sktj.model.UserModel;
import com.sktj.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

  private UserDao dao;
  private final UserRepository repository;
  private final AppProperties properties;
  private final Mapper mapper;

  @Autowired
  public UserService(UserDao dao, UserRepository repository,
      AppProperties properties, Mapper mapper) {
    this.dao = dao;
    this.repository = repository;
    this.properties = properties;
    this.mapper = mapper;
  }

  public List<UserModel> getAll() {
    List<UserModel> model = new ArrayList<>();
    repository.findAll().forEach(i -> model.add(mapper.mapUser(i)));
    return model;
  }

  public UserModel getUser(Long id) {
    User user = repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
    return mapper.mapUser(user);
  }

  public void save(User user)
      throws ForbiddenActionExeption {
    if (!user.getRole().equals("user") && !user.getEmail()
        .equals(properties.getSuperAdminEmail())) {
      throw new ForbiddenActionExeption("Validation exception" + HttpStatus.FORBIDDEN);
    }
    repository.save(user);
  }

  public void update(Long id, User user) {
    User editedUser = repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
    if (!editedUser.getEmail().equals(properties.getSuperAdminEmail())) {
      editedUser.setName(user.getName());
      editedUser.setSurname(user.getSurname());
      editedUser.setCardNumber(editedUser.getCardNumber());
      editedUser.setEmail(user.getEmail());
      editedUser.setCaveNotifications(user.getCaveNotifications());
      editedUser.setCaves(user.getCaves());
      editedUser.setClimbing(user.getClimbing());
      editedUser.setClimbingNotifications(user.getClimbingNotifications());
      editedUser.setOthers(user.getOthers());
      editedUser.setOtherNotifications(user.getOtherNotifications());
      repository.save(editedUser);
    }
  }

  public void grantAdminPermissions(Long id) {
    User user = repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
    if (!user.getEmail().equals(properties.getSuperAdminEmail())) {
      user.setRole("admin");
      repository.save(user);
    }
  }

  public void delete(Long userId) {
    User user = repository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
    if (!user.getEmail().equals(properties.getSuperAdminEmail())) {
      repository.delete(user);
    }
  }

  User findUserByEmail(String email) {
    return dao.findUserByEmail(email);
  }

  public List<UserModel> searchUsers(UserSpecification userSpecification) {
    List<UserModel> model = new ArrayList<>();
    repository.findAll(userSpecification).forEach(i -> model.add(mapper.mapUser(i)));
    return model;
  }
}
