package com.sktj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@NamedQuery(name = "CaveAchievements.findUsersCaveAchievs",
    query = "SELECT c FROM CaveAchievements c "
        + "left JOIN fetch c.country co "
        + "left JOIN fetch c.caveName cn"
        + "left JOIN fetch c.notificationAuthor na "
        + "left JOIN fetch c.authors ca where na.email = :email or ca.email =:email")
@Table(name = "cave")
public class CaveAchievements extends Achievement implements Serializable {

  @Column(name = "entry_timestamp")
  @NotNull
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
  LocalDateTime entryTimestamp;

  @Column(name = "exit_timestamp")
  @NotNull
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
  LocalDateTime exitTimestamp;

  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn
  Cave caveName;

  @Column
  @NotNull
  String reachedParts;

  @Column
  @NotNull
  String caveOvercomeStyle;
}
