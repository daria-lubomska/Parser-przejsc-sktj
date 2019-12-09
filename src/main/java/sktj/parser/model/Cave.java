package sktj.parser.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cave {

  private String name;
  private String region;

  public Cave(String name, String region) {
    this.name = name;
    this.region = region;
  }
}
