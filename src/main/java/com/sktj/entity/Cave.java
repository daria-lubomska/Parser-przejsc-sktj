package com.sktj.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
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
  Long id;

  @Column
  @NotNull
  String name;

  @Column
  @NotNull
  String region;

  @JsonIgnore
  @OneToMany(mappedBy = "caveName", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  Set<CaveAchievements> caves;

  public Cave(@NotNull String name, @NotNull String region) {
    this.name = name;
    this.region = region;
  }

}
