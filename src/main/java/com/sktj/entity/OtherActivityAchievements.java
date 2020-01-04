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
@Entity
@NamedQuery(name = "OtherActivityAchievements.findUserOtherAchievs",
    query = "SELECT c FROM OtherActivityAchievements c "
        + "left JOIN fetch c.country co "
        + "left JOIN fetch c.notificationAuthor na "
        + "left JOIN fetch c.authors ca where na.email = :email or ca.email =:email")
@Table(name = "other")
public class OtherActivityAchievements extends AchievementDetails implements Serializable {

  @Column
  @NotNull
  String achievementDescription;

  @Column
  @NotNull
  String category; //TODO enum?
}
