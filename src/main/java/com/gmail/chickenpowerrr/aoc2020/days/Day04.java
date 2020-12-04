package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

  private boolean hasAllFields(Map<String, String> passport) {
    return passport.containsKey("byr") && passport.containsKey("iyr") && passport.containsKey("eyr")
        && passport.containsKey("hgt") && passport.containsKey("hcl") && passport.containsKey("ecl")
        && passport.containsKey("pid");
  }

  private boolean hasValidBirth(Map<String, String> passport) {
    if (!passport.containsKey("byr")) {
      return false;
    }

    try {
      int byr = Integer.parseInt(passport.get("byr"));
      return 1920 <= byr && 2002 >= byr;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private boolean hasValidIssue(Map<String, String> passport) {
    if (!passport.containsKey("iyr")) {
      return false;
    }

    try {
      int byr = Integer.parseInt(passport.get("iyr"));
      return 2010 <= byr && 2020 >= byr;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private boolean hasValidExpiration(Map<String, String> passport) {
    if (!passport.containsKey("eyr")) {
      return false;
    }

    try {
      int byr = Integer.parseInt(passport.get("eyr"));
      return 2020 <= byr && 2030 >= byr;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private boolean hasValidHeight(Map<String, String> passport) {
    if (!passport.containsKey("hgt")) {
      return false;
    }

    String heightString = passport.get("hgt");
    if (heightString.endsWith("cm")) {
      try {
        int hgt = Integer.parseInt(passport.get("hgt").substring(0, heightString.length() - 2));
        return 150 <= hgt && 193 >= hgt;
      } catch (NumberFormatException e) {
        return false;
      }
    } else if (heightString.endsWith("in")) {
      try {
        int hgt = Integer.parseInt(passport.get("hgt").substring(0, heightString.length() - 2));
        return 59 <= hgt && 76 >= hgt;
      } catch (NumberFormatException e) {
        return false;
      }
    } else {
      return false;
    }
  }

  private boolean hasValidHair(Map<String, String> passport) {
    if (!passport.containsKey("hcl")) {
      return false;
    }

    return passport.get("hcl").matches("^#([a-f0-9]{6})$");
  }

  private boolean hasValidEye(Map<String, String> passport) {
    if (!passport.containsKey("ecl")) {
      return false;
    }

    Set<String> valid = Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
    return valid.contains(passport.get("ecl"));
  }

  private boolean hasValidPid(Map<String, String> passport) {
    if (!passport.containsKey("pid")) {
      return false;
    }

    return passport.get("pid").matches("^\\d{9}$");
  }

  private boolean hasAllValidFields(Map<String, String> passport) {
    return hasValidBirth(passport) && hasValidIssue(passport) && hasValidExpiration(passport)
        && hasValidHeight(passport) && hasValidHair(passport) && hasValidEye(passport)
        && hasValidPid(passport);
  }

  @Override
  public void partOne() {
    long validPassports = getPassports(this.fileHelper.readFileLines("day04/input")
        .collect(Collectors.toList())).stream().filter(this::hasAllFields).count();
    System.out.println(validPassports);
  }

  @Override
  public void partTwo() {
    long validPassports = getPassports(this.fileHelper.readFileLines("day04/input")
        .collect(Collectors.toList())).stream().filter(this::hasAllValidFields).count();
    System.out.println(validPassports);
  }
}
