package com.sktj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
public class Achievement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  Long id;

  @Setter
  @Column(name = "notification_timestamp")
  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime notificationTimestamp;

  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn
  Country country;

  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn
  User notificationAuthor;

  @Setter
  @Column
  String anotherAuthors;

  @Setter
  @Column(length = 1000)
  String comment;

  @Setter
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable
  Set<User> authors = new HashSet<>();

  public void setCountry(Country country) {
    if (Objects.equals(this.country,country))
      return;
    this.country = country;
  }

  public void setNotificationAuthor(User notificationAuthor) {
    if (Objects.equals(this.notificationAuthor,notificationAuthor))
      return;
    this.notificationAuthor = notificationAuthor;
  }
}
