package com.sktj.controller;

import com.sktj.entity.User;
import com.sktj.exception.ForbiddenActionExeption;
import com.sktj.model.UserModel;
import com.sktj.service.UserService;
import com.sktj.util.Mappings;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(Mappings.USERS)
public class UserController {

  private final UserService service;

  @Autowired
  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping
  public List<UserModel> getAll() {
    return service.getAll();
  }

  @GetMapping(Mappings.USER_ID)
  public ResponseEntity<UserModel> getUser(@PathVariable(value = "userId") Long id) {
    log.info("User with id {} fetched", id);
    return ResponseEntity.ok(service.getUser(id));
  }

  @PostMapping(Mappings.ADD_NEW)
  public ResponseEntity<?> save(@Valid @RequestBody User user) throws ForbiddenActionExeption {
    service.save(user);
    log.info("User {} {} saved successfully", user.getSurname(), user.getName());
    return new ResponseEntity<User>(HttpStatus.CREATED);
  }

  @PutMapping(Mappings.EDIT_USER)
  public ResponseEntity<User> update(@PathVariable("userId") long id,
      @Valid @RequestBody User user) {
    service.update(id, user);
    log.info("User with id {} updated successfully", id);
    return ResponseEntity.ok().build();
  }
  @PatchMapping(Mappings.GRANT_ADMIN)
  public ResponseEntity<User> grantAdminPermissions(@PathVariable("userId") long id) {
    service.grantAdminPermissions(id);
    log.info("Admin permissions granted for User with id {} ", id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping(Mappings.DELETE_USER)
  public ResponseEntity<?> delete(@PathVariable("userId") Long id) {
    service.delete(id);
    log.info("User with id {} removed successfully", id);
    return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
  }
}
