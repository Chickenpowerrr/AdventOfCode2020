package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.Util;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day13 implements Day {

  private final FileHelper fileHelper;

  public Day13() {
    this.fileHelper = new FileHelper();
  }

  @Override
  public void partOne() {
    int targetBus = 1000511;
    Map.Entry<Integer, Integer> entry = this.fileHelper.readFileLines("day13/input")
        .flatMap(s -> Arrays.stream(s.split(",")))
        .filter(s -> !s.equals("x"))
        .mapToInt(Integer::parseInt)
        .mapToObj(i -> new SimpleEntry<>(i, i - targetBus % i))
        .min(Comparator.comparingInt(SimpleEntry::getValue)).get();

    System.out.println(entry.getKey() * entry.getValue());
  }

  @Override
  public void partTwo() {
    long result = chineseRemainder(
        getRequirements(this.fileHelper.readFileLines("day13/input")
            .flatMap(s -> Arrays.stream(s.split(",")))
            .map(i -> {
              try {
                return Integer.parseInt(i);
              } catch (NumberFormatException e) {
                return null;
              }
            }).collect(Collectors.toList())));

    System.out.println(result);
  }

  private List<Requirement> getRequirements(List<Integer> busLines) {
    List<Requirement> requirements = new ArrayList<>();
    for (int i = 0; i < busLines.size(); i++) {
      if (busLines.get(i) != null) {
        requirements.add(new Requirement(busLines.get(i) - i, busLines.get(i)));
      }
    }

    return requirements;
  }

  // https://rosettacode.org/wiki/Chinese_remainder_theorem#Java
  public static long chineseRemainder(List<Requirement> requirements) {
    long prod = requirements.stream().mapToLong(Requirement::getMod)
        .reduce(1, (i, j) -> i * j);

    long p, sm = 0;
    for (Requirement requirement : requirements) {
      p = prod / requirement.getMod();
      sm += requirement.getBase() * Util.modInverse(p, requirement.getMod()) * p;
    }
    return sm % prod;
  }

  private static class Requirement {
    private final long base;
    private final long mod;

    public Requirement(long base, long mod) {
      this.base = base;
      this.mod = mod;
    }

    public long getBase() {
      return base;
    }

    public long getMod() {
      return mod;
    }

    @Override
    public String toString() {
      return "Requirement{" +
          "base=" + base +
          ", mod=" + mod +
          '}';
    }
  }
}
