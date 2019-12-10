package sktj.parser.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
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
@NamedQueries({
    @NamedQuery(
        name = "User.findUserByEmail",
        query = "SELECT u FROM User u WHERE u.email LIKE :email"),
    @NamedQuery(
        name = "User.findByNameAndSurname",
        query = "SELECT u FROM User u WHERE u.name LIKE :name AND u.surname LIKE :surname")})
@Table(name = "users", indexes = {@Index(name = "user_email", columnList = "email")})
public class User {

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
  @OneToMany(mappedBy = "notificationAuthor", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  List<CaveAchievements> caveNotifications = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "notificationAuthor", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  List<ClimbingAchievements> climbingNotifications = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "notificationAuthor", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  List<OtherActivityAchievements> otherNotifications = new ArrayList<>();

  @JsonIgnore
  @ManyToMany(mappedBy = "authors", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  List<CaveAchievements> caves = new ArrayList<>();

  @JsonIgnore
  @ManyToMany(mappedBy = "authors", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  List<ClimbingAchievements> climbs = new ArrayList<>();

  @JsonIgnore
  @ManyToMany(mappedBy = "authors", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  List<OtherActivityAchievements> others = new ArrayList<>();

}

