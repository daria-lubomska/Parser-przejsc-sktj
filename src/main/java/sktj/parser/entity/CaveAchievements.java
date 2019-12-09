package sktj.parser.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cave")
public class CaveAchievements implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column(name = "notification_timestamp")
  @NotNull
  private LocalDateTime notificationTimestamp;

  @Column(name = "entry_timestamp")
  @NotNull
  private LocalDateTime entryTimestamp;

  @Column(name = "exit_timestamp")
  @NotNull
  private LocalDateTime exitTimestamp;

  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "cavesOfCountry")
  private Country country;

  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "caves")
  private Cave caveName;

  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "notification_author")
  private User notificationAuthor;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "user_cave_achiev",
      joinColumns = @JoinColumn(name = "cave_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private List<User> authors = new ArrayList<>();

  @Column(name = "reached_parts")
  @NotNull
  private String reachedParts;

  @Column(name = "cave_overcome_style")
  @NotNull
  private int caveOvercomeStyle;

  @Column(name = "authors_from_another_clubs")
  private String authorsFromAnotherClubs;

  @Column(length = 1000)
  private String comment;

  public CaveAchievements() {
  }
}
