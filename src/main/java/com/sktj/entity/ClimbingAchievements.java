package com.sktj.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NamedQuery(name = "ClimbingAchievements.findUsersClimbingAchievs",
    query = "SELECT c FROM ClimbingAchievements c "
        + "left JOIN fetch c.country co "
        + "left JOIN fetch c.notificationAuthor na "
        + "left JOIN fetch c.authors ca where na.email = :email or ca.email =:email")
@Entity
@Table(name = "climbing")
public class ClimbingAchievements extends AchievementDetails implements Serializable {

  @Column(name = "route_name")
  @NotNull
  String routeName;

  @Column(name = "difficulty_grade")
  @NotNull
  String difficultyGrade;

  @Column
  @NotNull
  String wall;
}
