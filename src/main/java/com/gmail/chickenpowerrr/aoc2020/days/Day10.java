package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day10 implements Day {

  private final FileHelper fileHelper;

  public Day10() {
    this.fileHelper = new FileHelper();
  }

  @Override
  public void partOne() {
    Set<Integer> adapters = this.fileHelper.readFileLines("day10/input")
        .map(Integer::parseInt)
        .collect(Collectors.toSet());

    int oneDifference = 0;
    int threeDifference = 0;

    int previousCursor = -1;
    int cursor = 0;
    while (previousCursor != cursor) {
      previousCursor = cursor;

      for (int i = 1; i <= 3; i++) {
        if (adapters.contains(cursor + i)) {
          if (i == 1) {
            oneDifference++;
          } else if (i == 3) {
            threeDifference++;
          }

          cursor += i;
          break;
        }
      }
    }

    threeDifference++;
    System.out.println(oneDifference * threeDifference);
  }

  @Override
  public void partTwo() {
    Set<Integer> adapters = this.fileHelper.readFileLines("day10/input")
        .map(Integer::parseInt)
        .collect(Collectors.toSet());
    System.out.println(getPossibilities(adapters, 0, new HashMap<>()));
  }

  private long getPossibilities(Set<Integer> adapters, int target, Map<Integer, Long> results) {
    if (results.containsKey(target)) {
      return results.get(target);
    }

    long count = 0;

    for (int i = 1; i <= 3; i++) {
      if (adapters.contains(target + i)) {
        count += getPossibilities(adapters, target + i, results);
      }
    }

    count = count == 0 ? 1 : count;
    results.put(target, count);
    return count;
  }
}
