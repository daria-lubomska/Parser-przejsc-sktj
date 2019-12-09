package sktj.parser.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

  private String name;
  private String surname;
  private String email;

  public User(String name, String surname, String email) {
    this.name = name;
    this.surname = surname;
    this.email = email;
  }
}
