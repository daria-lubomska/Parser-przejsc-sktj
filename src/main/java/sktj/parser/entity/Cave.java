package sktj.parser.entity;

import java.io.Serializable;
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
    name = "Cave.findByNameAndRegion",
    query = "SELECT c FROM Cave c WHERE c.name = :name and c.region = :region")
@Table(name = "cave_name", indexes = {
    @Index(name = "nameCave", columnList = "name"),
    @Index(name = "regionCave", columnList = "region")})
public class Cave implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column
  @NotNull
  private String name;

  @Column
  @NotNull
  private String region;

  @OneToMany(mappedBy = "caveName", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<CaveAchievements> caves;

  public Cave() {
  }

  public Cave(@NotNull String name, @NotNull String region) {
    this.name = name;
    this.region = region;
  }
}
