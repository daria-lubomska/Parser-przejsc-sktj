package sktj.parser.service.parser;

public enum CaveAchievAttributes {
  NOTIFICATION_TIME(0),
  ENTRY_TIME(1),
  EXIT_TIME(2),
  CAVE_NAME(3),
  REACHED_PARTS(4),
  CAVE_OVERCOME_STYLE(5),
  COUNTRY(6),
  REGION(7),
  AUTHORS(8),
  ANOTHER_AUTHORS(9),
  COMMENT(10),
  NOTIFICATION_AUTHOR(11);
  private int code;

  private CaveAchievAttributes(int code) {
    this.code = code;
  }

  public int getCode(){
    return code;
  }

}
