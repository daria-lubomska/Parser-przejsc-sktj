package com.sktj.entity;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
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
@NamedQuery(name = "ClimbingAchievements.findUsersClimbingAchievs",
    query = "SELECT c FROM ClimbingAchievements c "
        + "left JOIN fetch c.country co "
        + "left JOIN fetch c.notificationAuthor na "
        + "left JOIN fetch c.authors ca where na.email = :email or ca.email =:email")
@Entity
@Table(name = "climbing")
public class ClimbingAchievements extends Achievement implements Serializable {

  @Column
  @NotNull
  LocalDate date;

  @Column
  Duration duration;

  @Column(name = "route_name")
  @NotNull
  String routeName;

  @Column(name = "difficulty_grade")
  @NotNull
  String difficultyGrade;

  @Column
  @NotNull
  String wall;

  @Column
  @NotNull
  String region;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "user_climb",
      joinColumns = @JoinColumn(name = "climbing_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  Set<User> authors = new HashSet<>();
}
