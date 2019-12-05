package sktj.parser.entity;

public enum CaveOvercomeStyle {

  KURS(1),
  EKSPLORACJA(2),
  KARTOWANIE(3),
  INNE(4),
  NAUKOWE(5),
  REKREACJA(6),
  SZKOLENIOWY(7),
  SPORTOWY(8);

  private final int type;

  CaveOvercomeStyle(int type) {
    this.type = type;
  }

  public int getType() {
    return type;
  }
}
