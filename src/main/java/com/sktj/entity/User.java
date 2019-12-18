package com.sktj.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
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
    name = "User.findUserByEmail",
    query = "SELECT u FROM User u WHERE u.email = :email")
@Table(name = "users", indexes = {
    @Index(name = "user_email", columnList = "email")})
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  Long id;

  @Column
  @NotNull
  String role;

  @Column
  @NotNull
  int cardNumber;

  @Column
  @NotNull
  private String name;

  @Column
  @NotNull
  String surname;

  @Column
  @NotNull
  String email;

  @JsonIgnore
  @OneToMany(mappedBy = "notificationAuthor", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      orphanRemoval = true)
  Set<CaveAchievements> caveNotifications = new HashSet<>();

  @JsonIgnore
  @OneToMany(mappedBy = "notificationAuthor", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      orphanRemoval = true)
  Set<ClimbingAchievements> climbingNotifications = new HashSet<>();
  ;

  @JsonIgnore
  @OneToMany(mappedBy = "notificationAuthor", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      orphanRemoval = true)
  Set<OtherActivityAchievements> otherNotifications = new HashSet<>();

  @JsonIgnore
  @ManyToMany(mappedBy = "authors", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  Set<CaveAchievements> caves = new HashSet<>();

  @JsonIgnore
  @ManyToMany(mappedBy = "authors", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  Set<ClimbingAchievements> climbing = new HashSet<>();

  @JsonIgnore
  @ManyToMany(mappedBy = "authors", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  Set<OtherActivityAchievements> others = new HashSet<>();

}

