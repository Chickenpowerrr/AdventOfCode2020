package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15 implements Day {

  private final int[] input;

  public Day15() {
    this.input = new int[]{0, 6, 1, 7, 2, 19, 20};
  }

  @Override
  public void partOne() {
    System.out.println(playGame(this.input, 2020));
  }

  @Override
  public void partTwo() {
    System.out.println(playGame(this.input, 30000000));
  }

  private int playGame(int[] input, int times) {
    int previous = -1;
    Map<Integer, List<Integer>> spoken = new HashMap<>();
    for (int i = 0; i < input.length; i++) {
      previous = input[i];
      spoken.computeIfAbsent(input[i], a -> new ArrayList<>());
      spoken.get(input[i]).add(i);
    }

    for (int i = input.length; i < times; i++) {
      List<Integer> target = spoken.get(previous);

      if (target.size() == 1) {
        previous = 0;
      } else {
        if (target.size() > 2) {
          int a = target.get(target.size() - 2);
          int b = target.get(target.size() - 1);
          target.clear();
          target.add(a);
          target.add(b);
        }

        previous = target.get(target.size() - 1) - target.get(target.size() - 2);
      }

      spoken.computeIfAbsent(previous, a -> new ArrayList<>());
      spoken.get(previous).add(i);
    }

    return previous;
  }
}
