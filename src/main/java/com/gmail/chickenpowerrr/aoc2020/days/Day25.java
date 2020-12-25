package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;

public class Day25 implements Day {

  private static final int CARD_PUBLIC_KEY = 1327981;
  private static final int DOOR_PUBLIC_KEY = 2822615;

  @Override
  public void partOne() {
    int doorIterations = findLoopSize(7, DOOR_PUBLIC_KEY);

    System.out.println(applyLoopSize(CARD_PUBLIC_KEY, doorIterations));
  }

  @Override
  public void partTwo() {

  }

  private long applyLoopSize(int subject, int loopSize) {
    long value = 1;

    for (int loop = 0; loop < loopSize; loop++) {
      value *= subject;
      value %= 20201227;
    }

    return value;
  }

  private int findLoopSize(int subject, int target) {
    long value = 1;
    int loop = 0;

    while (value != target) {
      value *= subject;
      value %= 20201227;
      loop++;
    }

    return loop;
  }
}
