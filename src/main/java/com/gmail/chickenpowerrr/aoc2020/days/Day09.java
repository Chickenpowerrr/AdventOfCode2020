package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.ArrayList;
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

  private long getEncryptionWeakness(List<Long> session, int preambleLength) {
    long invalidNumber = getFirstInvalid(session, preambleLength);

    for (int i = 0; i < session.size(); i++) {
      if (invalidNumber > session.get(i)) {
        long targetCount = 0;
        List<Long> range = new ArrayList<>();

        for (int j = i; j < session.size() && targetCount < invalidNumber; j++) {
          targetCount += session.get(j);
          range.add(session.get(j));
        }

        if (targetCount == invalidNumber) {
          return range.stream().mapToLong(l -> l).min().getAsLong()
              + range.stream().mapToLong(l -> l).max().getAsLong();
        }
      }
    }

    return -1;
  }

  @Override
  public void partOne() {
    System.out.println(getFirstInvalid(this.fileHelper.readFileLines("day09/input")
        .map(Long::parseLong).collect(Collectors.toList()), 25));
  }

  @Override
  public void partTwo() {
    System.out.println(getEncryptionWeakness(this.fileHelper.readFileLines("day09/input")
        .map(Long::parseLong).collect(Collectors.toList()), 25));
  }
}
