package com.sktj.exception;

public class ForbiddenActionExeption extends Exception{

  public ForbiddenActionExeption() {
  }

  public ForbiddenActionExeption(String message) {
    super(message);
  }
}
