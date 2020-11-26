package com.gmail.chickenpowerrr.aoc2020.framework;

public class Util {

  @SuppressWarnings("unchecked")
  public static <T extends Throwable, U> U sneakyThrow(Throwable throwable) throws T {
    throw (T) throwable;
  }

  public static String zeroPrefixUntilLength(int target, int length) {
    String value = Integer.toString(target);
    int zerosToAdd = length - value.length();
    String prefix = "0".repeat(zerosToAdd);

    return prefix + value;
  }

  private Util() {

  }
}
