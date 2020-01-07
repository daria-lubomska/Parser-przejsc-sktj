package com.sktj.util;

public class Mappings {

  //general
  public static final String ADD_NEW = "/addNew";
  public static final String FILTER = "/filter";
  public static final String SEARCH = "/search";

  //users mappings
  public static final String USERS = "/users";
  public static final String USER_ID = "/id/{userId}";
  public static final String EDIT_USER = "/edit/{userID}";
  public static final String DELETE_USER = "/delete/{userId}";
  public static final String GRANT_ADMIN = "/grantAdminPermissions/{userId}";

  //live search mappings
  public static final String LIVE = "/livesearch";
  public static final String COUNTRIES = "/countries";

  //caves mappings
  public static final String CAVES = "/caves";
  public static final String GET_CAVE_ACHIEV = "/id/{caveAchievId}";
  public static final String DELETE_CAVE_ACHIEV = "/delete/{caveAchievId}";
  public static final String EDIT_CAVE_ACHIEV = "/edit/{caveAchievId}";
  public static final String CAVES_AND_NOTIF = "/cavesAndNotific";

  //climbing mappings
  public static final String CLIMBING = "/climbing";
  public static final String GET_CLIMBING = "/id/{climbingId}";
  public static final String DELETE_CLIMBING = "/delete/{climbingId}";
  public static final String EDIT_CLIMBING = "/edit/{climbingId}";
  public static final String CLIMBING_AND_NOTIF = "/climbingAndNotific";

  //others mappings
  public static final String OTHERS = "/others";
  public static final String GET_OTHER = "/id/{otherId}";
  public static final String DELETE_OTHER_ACHIEV = "/delete/{otherId}";
  public static final String EDIT_OTHER_ACHIEV = "/edit/{otherId}";
  public static final String OTHERS_AND_NOTIF = "/otherAndNotific";
}
