package sktj.parser.entity;

import lombok.Getter;

@Getter
public enum RegionPoland {

  NIZ_POLSKI("Niż Polski"), KARPATY("Karpaty"), PIENINY("Pieniny"),
  TATRY("Tatry"), NIECKA_NIDZIANSKA("Niecka Nidziańska"),
  REJON_SWIETOKRZYSKI("Region Świętokrzyski"), SUDETY("Sudety"),
  WYZYNA_SLASKO_KRAKOWSKA("Wyżyna Śląsko-Krakowska");

  private final String name;

  RegionPoland(String name) {
    this.name = name;
  }
}
