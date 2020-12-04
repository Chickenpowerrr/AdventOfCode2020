package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day04 implements Day {

  private final FileHelper fileHelper;

  public Day04() {
    this.fileHelper = new FileHelper();
  }

  private List<Map<String, String>> getPassports(List<String> lines) {
    List<Map<String, String>> passports = new ArrayList<>();
    passports.add(new HashMap<>());

    for (String line : lines) {
      if (line.isEmpty()) {
        passports.add(new HashMap<>());
      } else {
        Map<String, String> passport = passports.get(passports.size() - 1);
        for (String entry : line.split(" ")) {
          String[] entryPart = entry.split(":");
          passport.put(entryPart[0], entryPart[1]);
        }
      }
    }

    return passports;
  }

  private boolean isValidPassport(Map<String, String> passport) {
    return passport.containsKey("byr") && passport.containsKey("iyr") && passport.containsKey("eyr")
        && passport.containsKey("hgt") && passport.containsKey("hcl") && passport.containsKey("ecl")
        && passport.containsKey("pid");
  }

  @Override
  public void partOne() {
    long validPassports = getPassports(this.fileHelper.readFileLines("day04/input")
        .collect(Collectors.toList())).stream().filter(this::isValidPassport).count();
    System.out.println(validPassports);
  }

  @Override
  public void partTwo() {

  }
}
