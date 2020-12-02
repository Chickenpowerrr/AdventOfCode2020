package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;

public class Day02 implements Day {

  private final FileHelper fileHelper;

  public Day02() {
    this.fileHelper = new FileHelper();
  }

  @Override
  public void partOne() {
    System.out.println(this.fileHelper.readFileLines("day02/input")
        .map(passwordRule -> {
          String[] parts = passwordRule.split(" ");
          String[] occurrencesParts = parts[0].split("-");
          return new Password(Integer.parseInt(occurrencesParts[0]),
              Integer.parseInt(occurrencesParts[1]), parts[1].substring(0, parts[1].length() - 1),
              parts[2]);
        }).filter(Password::isValid).count());
  }

  @Override
  public void partTwo() {

  }

  private static class Password {
    private final int minOccurrences;
    private final int maxOccurrences;
    private final String mandatoryPattern;
    private final String password;

    public Password(int minOccurrences, int maxOccurrences, String mandatoryPattern,
        String password) {
      this.minOccurrences = minOccurrences;
      this.maxOccurrences = maxOccurrences;
      this.mandatoryPattern = mandatoryPattern;
      this.password = password;
    }

    public boolean isValid() {
      int occurrences = 0;
      for (int i = 0; i < this.password.length() - this.mandatoryPattern.length() + 1; i++) {
        String subject = this.password.substring(i, i + this.mandatoryPattern.length());
        if (subject.equals(this.mandatoryPattern)) {
          occurrences++;
        }
      }

      return occurrences >= this.minOccurrences && occurrences <= this.maxOccurrences;
    }
  }
}
