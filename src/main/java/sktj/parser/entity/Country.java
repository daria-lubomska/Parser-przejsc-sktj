package sktj.parser.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@NamedQuery(
    name = "Country.findCountryByName",
    query = "SELECT c FROM Country c WHERE c.name LIKE :name")
@Table(name = "country", indexes = {@Index(name = "country_name", columnList = "name")})
public class Country {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column
  @NotNull
  private String name;

  @OneToMany(mappedBy = "country", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<CaveAchievements> caves;

  public Country() {
  }

  public Country(@NotNull String name) {
    this.name = name;
  }
}
