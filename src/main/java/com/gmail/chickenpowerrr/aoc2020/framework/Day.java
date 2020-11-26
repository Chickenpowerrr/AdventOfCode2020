package com.gmail.chickenpowerrr.aoc2020.framework;

import java.util.Collection;
import java.util.Collections;

public interface Day {

  void partOne();

  void partTwo();

  default Collection<Integer> disabledParts() {
    return Collections.emptySet();
  }
}
