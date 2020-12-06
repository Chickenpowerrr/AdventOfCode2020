package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day06 implements Day {

  private final FileHelper fileHelper;

  public Day06() {
    this.fileHelper = new FileHelper();
  }

  @Override
  public void partOne() {
    List<String> lines = this.fileHelper.readFileLines("day06/input")
        .collect(Collectors.toList());
    List<Set<Character>> totalAnswers = new ArrayList<>();
    totalAnswers.add(new HashSet<>());

    for (String line : lines) {
      if (line.isEmpty()) {
        totalAnswers.add(new HashSet<>());
      }

      Set<Character> targetSet = totalAnswers.get(totalAnswers.size() - 1);
      for (Character c : line.toCharArray()) {
        targetSet.add(c);
      }
    }

    System.out.println(totalAnswers.stream().mapToInt(Set::size).sum());
  }

  @Override
  public void partTwo() {
    List<String> lines = this.fileHelper.readFileLines("day06/input")
        .collect(Collectors.toList());
    List<Set<Character>> totalAnswers = new ArrayList<>();
    boolean first = true;
    totalAnswers.add(new HashSet<>());

    for (String line : lines) {
      if (line.isEmpty()) {
        totalAnswers.add(new HashSet<>());
        first = true;
        continue;
      }

      Set<Character> targetSet = totalAnswers.get(totalAnswers.size() - 1);

      if (first) {
        for (Character c : line.toCharArray()) {
          targetSet.add(c);
        }
        first = false;
      } else {
        targetSet.removeIf(c -> !line.contains(c.toString()));
      }
    }

    System.out.println(totalAnswers.stream().mapToInt(Set::size).sum());
  }
}
