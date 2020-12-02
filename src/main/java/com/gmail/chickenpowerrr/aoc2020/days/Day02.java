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
          return new RentalPassword(Integer.parseInt(occurrencesParts[0]),
              Integer.parseInt(occurrencesParts[1]), parts[1].substring(0, parts[1].length() - 1),
              parts[2]);
        }).filter(RentalPassword::isValid).count());
  }

  @Override
  public void partTwo() {
    System.out.println(this.fileHelper.readFileLines("day02/input")
        .map(passwordRule -> {
          String[] parts = passwordRule.split(" ");
          String[] occurrencesParts = parts[0].split("-");
          return new CorporatePassword(Integer.parseInt(occurrencesParts[0]),
              Integer.parseInt(occurrencesParts[1]), parts[1].charAt(0),
              parts[2]);
        }).filter(CorporatePassword::isValid).count());
  }

  private static class RentalPassword {
    private final int minOccurrences;
    private final int maxOccurrences;
    private final String mandatoryPattern;
    private final String password;

    public RentalPassword(int minOccurrences, int maxOccurrences, String mandatoryPattern,
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

  private static class CorporatePassword {
    private final int firstPosition;
    private final int secondPosition;
    private final char targetCharacter;
    private final String password;

    public CorporatePassword(int firstPosition, int secondPosition, char targetCharacter,
        String password) {
      this.firstPosition = firstPosition;
      this.secondPosition = secondPosition;
      this.targetCharacter = targetCharacter;
      this.password = password;
    }

    public boolean isValid() {
      int occurrences = 0;
      if (this.password.charAt(this.firstPosition - 1) == this.targetCharacter) {
        occurrences++;
      }

      if (this.password.charAt(this.secondPosition - 1) == this.targetCharacter) {
        occurrences++;
      }

      return occurrences == 1;
    }
  }
}
