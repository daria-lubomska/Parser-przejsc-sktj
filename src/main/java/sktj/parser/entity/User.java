package sktj.parser.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users", indexes = {@Index(name = "user_email", columnList = "email")})
@NamedQueries({
    @NamedQuery(
        name = "User.findUserByEmail",
        query = "SELECT u FROM User u WHERE u.email LIKE :email"),
    @NamedQuery(
        name = "User.findByNameAndSurname",
        query = "SELECT u FROM User u WHERE u.name LIKE :name AND u.surname LIKE :surname"),
    @NamedQuery(
        name = "User.findUserForLiveSearch",
        query = "SELECT u FROM User u WHERE u.name LIKE CONCAT('%',:someChars,'%') or u.surname "
            + "LIKE CONCAT('%',:someChars,'%')")
})
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column
  @NotNull
  private String role;

  @Column
  @NotNull
  private int cardNumber;

  @Column
  @NotNull
  private String name;

  @Column
  @NotNull
  private String surname;

  @Column
  @NotNull
  private String email;

  @OneToMany(mappedBy = "notificationAuthor", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<CaveAchievements> caveNotifications = new ArrayList<>();

  @OneToMany(mappedBy = "notificationAuthor", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<ClimbingAchievements> climbingNotifications = new ArrayList<>();

  @OneToMany(mappedBy = "notificationAuthor", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<OtherActivityAchievements> otherNotifications = new ArrayList<>();

  @ManyToMany(mappedBy = "authors", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<CaveAchievements> caves = new ArrayList<>();

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "user_climbing_achiev",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "climb_id"))
  private List<ClimbingAchievements> climbs = new ArrayList<>();

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "user_other_achiev",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "other_id"))
  private List<OtherActivityAchievements> others = new ArrayList<>();

  public User() {
  }
}
