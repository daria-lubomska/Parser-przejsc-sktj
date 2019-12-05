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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "other")
public class OtherActivityAchievements implements Serializable {

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

  @Column(name = "achievement_description")
  @NotNull
  private String achievement;

  @Column
  @NotNull
  private String category; //TODO enum?

  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "notification_author")
  private User notificationAuthor;

  @ManyToMany(mappedBy = "others")
  private List<User> authors = new ArrayList<>();

  @Column(name = "authors_from_another_clubs")
  private String anotherAuthors;

  @Column(length = 1000)
  private String comment;

  public OtherActivityAchievements() {
  }
}
