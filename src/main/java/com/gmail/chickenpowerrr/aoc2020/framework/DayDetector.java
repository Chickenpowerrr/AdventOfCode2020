package com.gmail.chickenpowerrr.aoc2020.framework;

import java.lang.reflect.Constructor;

public class DayDetector {

  private static final String DAY_PREFIX = "com.gmail.chickenpowerrr.aoc2020.days.Day";

  public Day getDay(int day) throws ReflectiveOperationException {
    String dayNumber = Util.zeroPrefixUntilLength(day, 2);
    Class<?> dayClass = Class.forName(DAY_PREFIX + dayNumber);
    Constructor<?> dayConstructor = dayClass.getConstructor();
    return (Day) dayConstructor.newInstance();
  }
}
