package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.List;
import java.util.stream.Collectors;

public class Day01 implements Day {

  private final FileHelper fileHelper;

  public Day01() {
    this.fileHelper = new FileHelper();
  }

  @Override
  public void partOne() {
    List<Integer> integers = this.fileHelper.readFileLines("day01/input")
        .map(Integer::parseInt)
        .collect(Collectors.toList());

    for (int i = 0; i < integers.size() - 1; i++) {
      for (int j = i + 1; j < integers.size(); j++) {
        if (integers.get(i) + integers.get(j) == 2020) {
          System.out.println(integers.get(i) * integers.get(j));
          return;
        }
      }
    }
  }

  @Override
  public void partTwo() {
    List<Integer> integers = this.fileHelper.readFileLines("day01/input")
        .map(Integer::parseInt)
        .collect(Collectors.toList());

    for (int i = 0; i < integers.size() - 2; i++) {
      for (int j = i + 1; j < integers.size() - 1; j++) {
        for (int k = j + 1; k < integers.size(); k++) {
          if (integers.get(i) + integers.get(j) + integers.get(k) == 2020) {
            System.out.println(integers.get(i) * integers.get(j) * integers.get(k));
            return;
          }
        }
      }
    }
  }
}
