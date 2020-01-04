package com.sktj.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OtherAchievModel extends DetailedAchievModel {
  String achievementDescription;
  String category; //TODO enum?
}
