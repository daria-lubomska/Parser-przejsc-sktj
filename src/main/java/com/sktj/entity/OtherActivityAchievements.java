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
@Entity
@NamedQuery(name = "OtherActivityAchievements.findUserOtherAchievs",
    query = "SELECT c FROM OtherActivityAchievements c "
        + "left JOIN fetch c.country co "
        + "left JOIN fetch c.notificationAuthor na "
        + "left JOIN fetch c.authors ca where na.email = :email or ca.email =:email")
@Table(name = "other")
public class OtherActivityAchievements extends Achievement implements Serializable {

  @Column
  @NotNull
  LocalDate date;

  @Column
  Duration duration;

  @Column
  @NotNull
  String region;

  @Column
  @NotNull
  String achievementDescription;

  @Column
  @NotNull
  String category; //TODO enum?

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "user_other",
      joinColumns = @JoinColumn(name = "other_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  Set<User> authors = new HashSet<>();
}
