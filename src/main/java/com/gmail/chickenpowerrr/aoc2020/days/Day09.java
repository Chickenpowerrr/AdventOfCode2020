package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.List;
import java.util.stream.Collectors;

public class Day09 implements Day {

  private final FileHelper fileHelper;

  public Day09() {
    this.fileHelper = new FileHelper();
  }

  private long getFirstInvalid(List<Long> session, int preambleLength) {
    for (int i = preambleLength; i < session.size(); i++) {
      if (!isValid(session.get(i), session.subList(i - preambleLength, i))) {
        return session.get(i);
      }
    }

    return -1;
  }

  private boolean isValid(long target, List<Long> preamble) {
    for (int i = 0; i < preamble.size(); i++) {
      long cursor = preamble.get(i);
      long match = target - cursor;
      if (preamble.contains(match)) {
        if (cursor != match) {
          return true;
        } else {
          for (int j = i + 1; j < preamble.size(); j++){
            if (preamble.get(i) == match) {
              return true;
            }
          }
          return false;
        }
      }
    }

    return false;
  }

  @Override
  public void partOne() {
    System.out.println(getFirstInvalid(this.fileHelper.readFileLines("day09/input")
        .map(Long::parseLong).collect(Collectors.toList()), 25));
  }

  @Override
  public void partTwo() {

  }
}
