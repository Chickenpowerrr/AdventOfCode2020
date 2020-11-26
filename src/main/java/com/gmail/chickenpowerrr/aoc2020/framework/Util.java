package com.gmail.chickenpowerrr.aoc2020.framework;

public class Util {

  @SuppressWarnings("unchecked")
  public static <T extends Throwable, U> U sneakyThrow(Throwable throwable) throws T {
    throw (T) throwable;
  }

  private Util() {

  }
}
