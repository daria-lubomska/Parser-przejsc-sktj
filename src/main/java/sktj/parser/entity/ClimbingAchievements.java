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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "climbing")
public class ClimbingAchievements implements Serializable {

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

  @Column
  @NotNull
  private String region;

  @Column(name = "climbing_route_name")
  @NotNull
  private String climbingRouteName;

  @Column(name = "difficulty_grade")
  @NotNull
  private String difficultyGrade;

  @Column
  @NotNull
  private String wall;

  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "notification_author")
  private User notificationAuthor;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "user_climb",
      joinColumns = @JoinColumn(name = "climb_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  List<User> authors = new ArrayList<>();

  @Column(name = "authors_from_another_clubs")
  private String anotherAuthors;

  @Column(length = 1000)
  private String comment;

}
