package sktj.parser.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserLiveSearchView {

  private String name;
  private String surname;

  public UserLiveSearchView(String name, String surname) {
    this.name = name;
    this.surname = surname;
  }
}
